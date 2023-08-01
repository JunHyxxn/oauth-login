import React, {useEffect} from "react";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Paper from "@mui/material/Paper";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import {Container} from "@mui/material";
import styled from "styled-components";

const google = "/img/google.png";
const kakao = "/img/kakao.png";
const naver = "/img/naver.png";
const SImg = styled.img`
  width: 250px;
  height: 55px;
  margin: auto;
  margin-bottom: 15px;
  cursor: pointer;
`;

const AppLogin = () => {
  useEffect(() => {
    const footer = document.getElementsByTagName("footer")[0];
    footer.style.display = "none";

    return () => {
      footer.style.display = "flex";
    };
  });
  const clickHandler = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      email: data.get("email"),
      password: data.get("password"),
    });
  };
  const socialClickHandler = (event) => {
    console.log(event.target.alt);
  }

  return (
      <Container component="main" maxWidth="lg">
        <Box
            sx={{
              marginTop: 8,
            }}
        >
          <Grid container>
            <CssBaseline/>
            <Grid
                item
                xs={false}
                sm={4}
                md={7}
                sx={{
                  backgroundImage: "url(https://source.unsplash.com/random)",
                  backgroundRepeat: "no-repeat",
                  backgroundColor: (t) =>
                      t.palette.mode === "light"
                          ? t.palette.grey[50]
                          : t.palette.grey[900],
                  backgroundSize: "cover",
                  backgroundPosition: "center",
                }}
            />
            <Grid
                item
                xs={12}
                sm={8}
                md={5}
                component={Paper}
                elevation={6}
                square
            >
              <Box
                  sx={{
                    my: 8,
                    mx: 4,
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                  }}
              >
                <Typography component="h1" variant="h5">
                  Sign in
                </Typography>
                <Box
                    component="form"
                    noValidate
                    onSubmit={clickHandler}
                    sx={{mt: 1}}
                >
                  <TextField
                      margin="normal"
                      required
                      fullWidth
                      id="email"
                      label="Email Address"
                      name="email"
                      autoComplete="email"
                      autoFocus
                  />
                  <TextField
                      margin="normal"
                      required
                      fullWidth
                      name="password"
                      label="Password"
                      type="password"
                      id="password"
                      autoComplete="current-password"
                  />
                  <Button
                      type="submit"
                      fullWidth
                      variant="contained"
                      sx={{mt: 3, mb: 2}}
                  >
                    Sign In
                  </Button>
                  <Grid container>
                    <SImg src={google} alt="google"
                          onClick={socialClickHandler}/>
                    <SImg src={kakao} alt="kakao" onClick={socialClickHandler}/>
                    <SImg src={naver} alt="naver" onClick={socialClickHandler}/>
                  </Grid>
                </Box>
              </Box>
            </Grid>
          </Grid>
        </Box>
      </Container>
  );
}

export default AppLogin;