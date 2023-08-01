import React from 'react';
import styled from "styled-components";

const BodyRoot = styled.body`
  min-height: 705.5px;
  margin-left: 120px;
  margin-right: 120px;
  margin-top: 15px;
  padding: 0;
  
`

const Body = (props) => {
  return (
      <BodyRoot>
        {props.children}
      </BodyRoot>
  );
};

export default Body;