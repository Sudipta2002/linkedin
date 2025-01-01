import React, { useState } from 'react'
import { Button } from '../components/Button/Button';
import { Input } from '../../../components/Input/Input';
import Layout from '../components/AuthenticationLayout/AuthenticationLayout';
import { Box } from '../components/Box/Box';
import classes  from './ResetPassword.module.scss';
import { useNavigate } from 'react-router-dom';
export default function ResetPassword() {
    const [emailSent, setEmailSent] = useState(false);
    const [email, setEmail] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const navigate = useNavigate();
    return (
    <div className={classes.root}>
      <Box>
        <h1>Reset Password</h1>
        {!emailSent ? (
          <form
            // onSubmit={async (e) => {
            //   e.preventDefault();
            //   setIsLoading(true);
            //   const email = e.currentTarget.email.value;
            //   await sendPasswordResetToken(email);
            //   setEmail(email);
            //   setIsLoading(false);
            // }}
          >
            <p>
              Enter your email and we’ll send a verification code if it matches an existing LinkedIn
              account.
            </p>
            <Input key="email" name="email" type="email" label="Email" />
            <p style={{ color: "red" }}>{errorMessage}</p>
            <Button type="submit" disabled={isLoading}>
              Next
            </Button>
            <Button
              outline
              onClick={() => {
                navigate("/authentication/login");
              }}
              disabled={false}
            >
              Back
            </Button>
          </form>
        ) : (
          <form
            // onSubmit={async (e) => {
            //   e.preventDefault();
            //   setIsLoading(true);
            //   const code = e.currentTarget.code.value;
            //   const password = e.currentTarget.password.value;
            //   await resetPassword(email, code, password);
            //   setIsLoading(false);
            // }}
          >
            <p>Enter the verification code we sent to your email and your new password.</p>
            <Input type="text" label="Verification code" key="code" name="code" />
            <Input
              label="New password"
              name="password"
              key="password"
              type="password"
              id="password"
            />
            <p style={{ color: "red" }}>{errorMessage}</p>
            <Button type="submit" disabled={false}>
              {false ? "..." : "Reset Password"}
            </Button>
            <Button
              outline
              type="button"
              onClick={() => {
                setEmailSent(false);
                setErrorMessage("");
              }}
              disabled={false}
            >
              {false ? "..." : "Back"}
            </Button>
          </form>
        )}
      </Box>
    </div>
  )
}
