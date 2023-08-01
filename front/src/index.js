import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import {RecoilRoot} from "recoil";
import {BrowserRouter} from "react-router-dom";
import {StyledEngineProvider} from "@mui/styled-engine-sc";
import {createTheme, ThemeProvider} from '@mui/material/styles';

const theme = createTheme({
  palette: {
    primary: {
      main: '#ffffff',
      light: '#F6F4EB',
      dark: '#dddbd3',
      contrastText: '#000000',
    },
    secondary: {
      main: "#91C8E4",
      light: "#deeef6",
      dark: "#4682A9",
      contrastText: "#577888"
    }
  },
});

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <ThemeProvider theme={theme}>
      <StyledEngineProvider injectFirst>
        <BrowserRouter>
          <RecoilRoot>
            <App/>
          </RecoilRoot>
        </BrowserRouter>
      </StyledEngineProvider>
    </ThemeProvider>
);

