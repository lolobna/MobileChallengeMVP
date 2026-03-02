package com.example.mobilechallengemvp.ui.TrendRepos;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.mobilechallengemvp.MyApp;
import com.example.mobilechallengemvp.data.model.Repo;
import com.example.mobilechallengemvp.databinding.FragmentRepoBinding;
import java.util.List;
import javax.inject.Inject;

public class RepoFragment extends Fragment implements RepoContract.View {

    @Inject
    RepoPresenter presenter;

    private FragmentRepoBinding binding;
    private RepoAdapter adapter;
    private int currentPage = 1;
    private boolean isLoading = false;

    public RepoFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Initialisation du binding
        binding = FragmentRepoBinding.inflate(inflater, container, false);
        return binding.getRoot(); // Retourne la vue racine du fragment
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Injection Dagger2 pour fournir le presenter
        ((MyApp) requireActivity().getApplication())
                .getAppComponent()
                .inject(this);

        presenter.attach(this); // Attacher la view au presenter
        adapter = new RepoAdapter(); // Création de l’adapter pour le RecyclerView
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter); // Liaison de l’adapter au RecyclerView
        // Bouton pour remonter en haut de la liste
        binding.btnScrollTop.setOnClickListener(v ->
                binding.recyclerView.smoothScrollToPosition(0)
        );
        // Listener pour détecter le scroll afin de gérer
        // 1. Affichage du bouton "Scroll Top"
        // 2. Pagination (chargement automatique de la page suivante)
        binding.recyclerView.addOnScrollListener(new androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull androidx.recyclerview.widget.RecyclerView rv, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
                if (layoutManager != null) {
                    // Affiche le bouton scrollTop si on n’est pas tout en haut
                    binding.btnScrollTop.setVisibility(layoutManager.findFirstVisibleItemPosition() > 3 ? View.VISIBLE : View.GONE);
                    if (!isLoading) { // On ne charge pas une nouvelle page si une requête est déjà en cours
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();
                        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                        // Si on atteint la fin de la liste, on charge la page suivante
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0)
                            loadPage();
                    }
                }
            }
        });

        loadPage(); // Chargement de la première page de repos
    }
    // Méthode pour charger une page
    private void loadPage() {
        isLoading = true; // On bloque le scroll pour éviter plusieurs requêtes simultanées
        adapter.addLoadingFooter(); // Ajoute un footer avec ProgressBar pour indiquer le chargement
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            presenter.loadRepos(currentPage); // Demande au presenter de charger les repos de la page courante
        }
    }
    @Override
    public void showRepos(List<Repo> repos) {
        adapter.removeLoadingFooter(); // Retire le ProgressBar
        adapter.addRepos(repos);       // Ajoute les nouveaux repos à la liste
        isLoading = false;             // Débloque le scroll pour permettre le chargement d'une nouvelle page
        currentPage++;                 // Incrémente la page pour la prochaine requête
    }
    @Override
    public void showError(String message) {
        adapter.removeLoadingFooter(); // Retire le footer de chargement en cas d’erreur
        isLoading = false;             // Débloque le scroll pour permettre de réessayer
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show(); // Affiche l’erreur
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detach(); // Détache le presenter pour éviter les leaks
        binding = null;     // Supprime le binding pour éviter les leaks de la vue
    }
}