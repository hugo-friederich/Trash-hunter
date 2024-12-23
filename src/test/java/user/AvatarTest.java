package user;


import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.trash_hunter.user.Avatar;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


class AvatarTest {
    private Avatar avatar; // Remplacez par le nom de votre classe

    @BeforeEach
    void setUp() {
        avatar = new Avatar("Bob","Animated"); // Initialisation de l'objet avant chaque test
    }

    @Test
    public void shouldLoadSprites() {
        // Cas positif : Vérifie que les images sont chargées correctement
        assertDoesNotThrow(() -> avatar.getAnimatedDiverSprites());

        // Vérification que les images ne sont pas nulles après le chargement
        Assertions.assertNotNull(avatar.left1);
        Assertions.assertNotNull(avatar.left2);
        assertNotNull(avatar.left3);
        assertNotNull(avatar.left4);
        assertNotNull(avatar.right1);
        assertNotNull(avatar.right2);
        assertNotNull(avatar.right3);
        assertNotNull(avatar.right4);
    }

    @Test
    void testGetAnimatedDiverSprites_FileNotFound() {
        // Cas négatif : Simule une situation où les fichiers d'image n'existent pas
        // Cela nécessite de manipuler le chemin ou de simuler le comportement de ImageIO
        // On pourrait utiliser un mock pour simuler ImageIO dans un cas réel
        assertThrows(IOException.class, () -> {
            // Simuler une exception ici (ceci est juste un exemple, le code réel peut varier)
            ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("/animatedDiver/nonExistentFile")));
        });
    }

    @Test
    void testGetAnimatedDiverSprites_InvalidImageFormat() {
        // Cas négatif : Vérifie le comportement lorsque le format de l'image est invalide
        // Ici aussi, cela nécessiterait de simuler une exception ou un comportement
        assertThrows(IOException.class, () -> {
            // Simuler une exception ici
            ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("/animatedDiver/invalidFormat")));
        });
    }

    // Ajoutez d'autres tests pour vérifier la compatibilité, la performance, etc.
}
