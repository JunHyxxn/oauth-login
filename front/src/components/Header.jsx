import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import Container from '@mui/material/Container';
import Button from '@mui/material/Button';
import MenuItem from '@mui/material/MenuItem';
import {Stack} from "@mui/material";
import {useNavigate} from 'react-router-dom';

const pages = [
  {name: "Home", link: "/"},
  {name: "GitHub", link: "https://github.com/JunHyxxn/oauth-login"},
  {name: "Notion", link: "https://JunHyxxn.notion.site"},
];

const Header = () => {
  const navigate = useNavigate();

  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };
  const goToLink = (link) => {
    if (link === "/") {
      navigate(link);
    } else {
      window.open(link, "_blank");
    }
  };
  const goToLogin = () => {
    navigate("/login");
  }
  const goToSignup = () => {
    navigate("/signup");
  }

  return (
      <AppBar position="static" color="secondary">
        <Container maxWidth="xl">
          <Toolbar disableGutters>
            <Typography
                variant="h6"
                noWrap
                component="a"
                href="/"
                sx={{
                  mr: 2,
                  display: {xs: 'none', md: 'flex'},
                  fontFamily: 'monospace',
                  fontWeight: 700,
                  letterSpacing: '.3rem',
                  textDecoration: 'none',
                }}
            >
              Jun
            </Typography>
            <Typography
                variant="h6"
                noWrap
                component="a"
                href="/"
                color="secondary.dark"
                sx={{
                  mr: 2,
                  display: {xs: 'none', md: 'flex'},
                  fontFamily: 'monospace',
                  fontWeight: 700,
                  letterSpacing: '.3rem',
                  textDecoration: 'none',
                }}
            >
              Hyxxn
            </Typography>

            <Box sx={{flexGrow: 1, display: {xs: 'flex', md: 'none'}}}>
              <IconButton
                  size="large"
                  aria-label="account of current user"
                  aria-controls="menu-appbar"
                  aria-haspopup="true"
                  onClick={handleOpenNavMenu}
              >
                <MenuIcon/>
              </IconButton>
              <Menu
                  id="menu-appbar"
                  anchorEl={anchorElNav}
                  anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'left',
                  }}
                  keepMounted
                  transformOrigin={{
                    vertical: 'top',
                    horizontal: 'left',
                  }}
                  open={Boolean(anchorElNav)}
                  onClose={handleCloseNavMenu}
                  sx={{
                    display: {xs: 'block', md: 'none'},
                  }}
              >
                {pages.map((page) => (
                    <MenuItem key={page.name} onClick={handleCloseNavMenu}>
                      <Typography textAlign="center"><Button
                          onClick={() => goToLink(
                              page.link)}>{page.name}</Button></Typography>
                    </MenuItem>
                ))}
              </Menu>
            </Box>
            <Typography
                variant="h5"
                noWrap
                component="a"
                href="/"
                sx={{
                  mr: 2,
                  display: {xs: 'flex', md: 'none'},
                  flexGrow: 1,
                  fontFamily: 'monospace',
                  fontWeight: 700,
                  letterSpacing: '.3rem',
                  textDecoration: 'none',
                }}
            >
              JunHyxxn
            </Typography>
            <Box sx={{flexGrow: 1, display: {xs: 'none', md: 'flex'}}}>
              {pages.map((page) => (
                  <Button
                      key={page.name}
                      onClick={() => goToLink(page.link)}
                      sx={{my: 2, color: 'white', display: 'block'}}
                  >
                    {page.name}
                  </Button>
              ))}
            </Box>

            <Box sx={{flexGrow: 0}}>
              <Stack spacing={2} direction="row">
                <Button variant="text" sx={{color: 'white'}}
                        onClick={() => goToLogin()}>Login</Button>
                <Button variant="text" sx={{color: 'white'}}
                        onClick={() => goToSignup()}>Sign Up</Button>
              </Stack>
            </Box>

          </Toolbar>
        </Container>
      </AppBar>
  );
}
export default Header;