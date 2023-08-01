import React, {useEffect} from "react";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Paper from "@mui/material/Paper";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import {Container, InputLabel, Select} from "@mui/material";
import styled from "styled-components";
import MenuItem from "@mui/material/MenuItem";

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

const AppSignUp = () => {
  useEffect(() => {
    const footer = document.getElementsByTagName("footer")[0];
    footer.style.display = "none";

    return () => {
      footer.style.display = "flex";
    };
  });

  const [gender, setGender] = React.useState('');

  const handleChange = (event) => {
    setGender(event.target.value);
  };

  const clickHandler = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      email: data.get("email"),
      password: data.get("password"),
      name: data.get("name"),
      gender: `${gender}`,
      age: data.get("age"),
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
                xs={12}
                sm={8}
                md={5}
                component={Paper}
                elevation={6}
                square
                sx={{
                  margin: "auto"
                }}
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
                  Sign Up
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
                  <TextField
                      margin="normal"
                      // required
                      fullWidth
                      name="name"
                      label="name"
                      type="text"
                      id="name"
                  />
                  <InputLabel id="gender">Choose Gender</InputLabel>
                  <Select
                      placehold={"Choose Gender"}
                      fullWidth
                      labelId="gender"
                      id="gender"
                      value={gender}
                      label="gender"
                      onChange={handleChange}
                  >
                    <MenuItem value={"man"}>Man</MenuItem>
                    <MenuItem value={"woman"}>Woman</MenuItem>
                  </Select>
                  <TextField
                      margin="normal"
                      // required
                      fullWidth
                      name="age"
                      label="age"
                      type="text"
                      id="age"
                  />
                  <Button
                      type="submit"
                      fullWidth
                      variant="contained"
                      sx={{mt: 3, mb: 2}}
                  >
                    Sign Up
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

export default AppSignUp;