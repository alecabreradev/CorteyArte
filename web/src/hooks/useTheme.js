import { useEffect, useState } from "react";

const STORAGE_KEY = "theme";

function readStoredTheme() {
  try {
    return localStorage.getItem(STORAGE_KEY);
  } catch {
    return null;
  }
}

function systemPrefersDark() {
  return window.matchMedia?.("(prefers-color-scheme: dark)").matches ?? false;
}

/**
 * Light/dark theme with three sources, in this order:
 * the user's saved choice, then the operating system preference.
 */
export default function useTheme() {
  const [theme, setTheme] = useState(
    () => readStoredTheme() ?? (systemPrefersDark() ? "dark" : "light")
  );

  useEffect(() => {
    document.documentElement.dataset.theme = theme;
  }, [theme]);

  // Follow the system setting only while the user has not chosen a theme
  useEffect(() => {
    const media = window.matchMedia?.("(prefers-color-scheme: dark)");
    if (!media) return undefined;
    const onChange = (e) => {
      if (!readStoredTheme()) setTheme(e.matches ? "dark" : "light");
    };
    media.addEventListener("change", onChange);
    return () => media.removeEventListener("change", onChange);
  }, []);

  const toggleTheme = () => {
    setTheme((current) => {
      const next = current === "dark" ? "light" : "dark";
      try {
        localStorage.setItem(STORAGE_KEY, next);
      } catch {
        /* storage not available: keep the theme for this visit only */
      }
      return next;
    });
  };

  return { theme, toggleTheme };
}
