import React from 'react';
import Header from "./Header";
import Footer from "./Footer";
import Body from "./Body";

const Layout = (props) => {
  return (
      <>
        <Header />
        <Body>{props.children}</Body>
        <Footer />
      </>
  );
};

export default Layout;