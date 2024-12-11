import React, { useState } from 'react'
import Layout from '../components/Layout/Layout'
import classes from  './Login.module.scss';
import { Box } from '../components/Box/Box';
import { Input } from '../components/Input/Input';
import { Button } from '../components/Button/Button';
import { Link } from 'react-router-dom';
import { Seperator } from '../components/Seperator/Seperator';
export default function Login() {
    const [errorMessage, setErrorMessage] = useState("");
  return (
    <Layout className={classes.root}>
      <Box>
        <h1>Sign in</h1>
        <p>Stay updated on your professional world.</p>
        <form >
          <Input label="Email" type="email" id="email" onFocus={() => setErrorMessage("")} />
          <Input
            label="Password"
            type="password"
            id="password"
            onFocus={() => setErrorMessage("")}
          />
          {errorMessage && <p className={classes.error}>{errorMessage}</p>}
          <Button type="submit" disabled={false}>
            {false ? "..." : "Sign in"}
          </Button>
          <Link to="/request-password-reset">Forgot password?</Link>
        </form>
        <Seperator>Or</Seperator>
        <div className={classes.register}>
          New to LinkedIn? <Link to="/signup">Join now</Link>
        </div>
      </Box>
    </Layout>
  )
}