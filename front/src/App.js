//react import
import React from "react";
import { Route, Routes } from "react-router-dom";

//pages import
import AppMain from "./pages/AppMain";
import Layout from "./components/Layout";
import AppError from "./pages/AppError";
import AppLogin from "./pages/AppLogin";
import AppSignUp from "./pages/AppSignUp";


function App() {
  return (
      <Layout>
        <Routes>
          <Route path="/" element={<AppMain />} />
          <Route path="/login" element={<AppLogin/> }/>
          <Route path="/signup" element={<AppSignUp/> }/>
          <Route path="/*" element={<AppError />} />
        </Routes>
      </Layout>
  );
}

export default App;