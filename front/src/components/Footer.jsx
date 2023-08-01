import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Link from '@mui/material/Link';

function Copyright() {
  return (
      <Typography variant="body2" color="text.secondary">
        {'Copyright © '}
        <Link color="inherit" href="https://github.com/JunHyxxn/oauth-login">
          JunHyxxn GitHub oauth-login project
        </Link>{' '}
        {new Date().getFullYear()}
        {'.'}
      </Typography>
  );
}


export default function StickyFooter() {
  return (
        <Box
            sx={{
              display: 'left',
              flexDirection: 'column',
            }}

        >
          <CssBaseline />
          <Box
              component="footer"
              sx={{
                py: 3,
                px: 2,
                mt: 'auto',
                backgroundColor: (theme) =>
                    theme.palette.mode === 'light'
                        ? theme.palette.grey[200]
                        : theme.palette.grey[800],
              }}
          >
            <Container maxWidth="sm" sx={{marginLeft: "0"}}>
              <Typography color="secondary.dark" variant="h4">JunHyxxn</Typography>

              <Typography color="secondary.contrastText" variant="caption">Contact to ihrug000@gmail.com</Typography>
              <Copyright />
            </Container>
          </Box>
        </Box>
  );
}