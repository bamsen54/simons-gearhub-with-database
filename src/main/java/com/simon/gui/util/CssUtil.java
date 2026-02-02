package com.simon.gui.util;

import javafx.scene.Parent;

public class CssUtil {

    public static void setCss(Parent node, String path) {
        String css = Thread.currentThread().getContextClassLoader().getResource(path.startsWith("/") ? path.substring(1) : path).toExternalForm();
        node.getStylesheets().add(css);
    }
}
