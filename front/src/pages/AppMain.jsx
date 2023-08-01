import React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import {Button, CardActions} from '@mui/material';
import Container from "@mui/material/Container";

const AppMain = () => {
  const userName = "User";
  return (
      <Container maxWidth="sm" sx={{
        m: "auto"
      }}>
        <Card sx={{maxWidth: 600, minHeight: 400}}>
            <CardMedia
                component="img"
                height="450"
                src="/img/rose.jpg"
            >
            </CardMedia>
            <CardContent>
              <Typography gutterBottom variant="h5" component="div">
                Welcome, {`${userName}` === "User" ? "User" : userName}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                This Project is OAuth Login Project.<br/>
                This Login Project that is created by using React and Spring Boot and OAuth2 Client. <br/>
                If you want to see the development process, check out the <a href="https://JunHyxxn.notion.site">Notion</a> page. <br/>
                Thank You! 😊

              </Typography>
            </CardContent>
          <CardActions>
            <Button size="small" color="secondary" onClick={() =>
            window.open("https://github.com/JunHyxxn/oauth-login")}>
              Code
            </Button>
          </CardActions>
        </Card>
      </Container>
  );
};

export default AppMain;