import React, { useState } from 'react'
import { Button } from '../components/Button/Button';
import Layout from '../components/Layout/Layout';
import { Box } from '../components/Box/Box';
import { Input } from '../components/Input/Input';
import classes from './Verify.module.scss';
export default function VerifyEmail() {
    const [errorMessage, setErrorMessage] = useState("");
    const [message, setMessage] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    return (
    <Layout className={classes.root}>
    <Box>
      <h1>Verify your Email</h1>
      <form
        onSubmit={async (e) => {
          e.preventDefault();
          setIsLoading(true);
          const code = e.currentTarget.code.value;
        //   await validateEmail(code);
          setIsLoading(false);
        }}
      >
        <p>Only one step left to complete your registration. Verify your email address.</p>
        <Input type="text" label="Verification code" key="code" name="code" />
        {message ? <p style={{ color: "green" }}>{message}</p> : null}
        {errorMessage ? <p style={{ color: "red" }}>{errorMessage}</p> : null}
        <Button type="submit" disabled={isLoading}>
          {isLoading ? "..." : "Validate email"}
        </Button>
        <Button
          outline
          type="button"
          onClick={() => {
            // sendEmailVerificationToken();
          }}
          disabled={isLoading}
        >
          {isLoading ? "..." : "Send again"}
        </Button>
      </form>
    </Box>
  </Layout>
  )
}
