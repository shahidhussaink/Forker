import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Shahid Hussain Khan
 * Date: April 20, 2017 4:20 PM
 */
public class Fork {

    private static List<String> parseFile(String fileName) throws IOException {
        ArrayList<String> repoList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                line.trim();
                if(line.endsWith(".git")) {
                    line = line.substring(0, line.length() - 4);
                    repoList.add(line);
                }
            }
        }
        return repoList;
    }

    public static void main(String... args) throws IOException {
        List<String> reposToFork = parseFile("repo_list.txt");
        GitHubClient client = new GitHubClient();
        String username = "<your github username>", password = "<your github password>";
        client.setCredentials(username, password);
        RepositoryService service = new RepositoryService(client);
        String owner = "<organization/owner>";
        for (String repo: reposToFork) {
            RepositoryId toBeForked = new RepositoryId(owner, repo);
            try {
                service.forkRepository(toBeForked, null);
                System.out.println(repo + " forked");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(repo + " could not be forked");
            }
        }
    }

}