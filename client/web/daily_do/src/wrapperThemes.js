export const wrapperThemes = {
    light: {
      fontColor: '#000000',
      backgroundColor: '#F6F6F6',
    },
    dark: {
      fontColor: '#ffffff',
      backgroundColor: '#1D1D1D',
    },
  };
  
  export const ThemeContext = React.createContext(
    wrapperThemes.light // 기본값
  );