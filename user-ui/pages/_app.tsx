import { ChakraProvider } from "@chakra-ui/react";
import {SessionProvider} from "next-auth/react"
import { AppProps } from "next/app";
import type {Session} from "next-auth"
import Head from "next/head";

const MyApp = ({Component, pageProps: {session, ...pageProps},}: AppProps<{ session: Session }>) => {
  return (
    <ChakraProvider>
      <Head>
        <meta
          name="viewport"
          content="minimum-scale=1, initial-scale=1, width=device-width, shrink-to-fit=no, viewport-fit=cover"
        />
      </Head>
        <SessionProvider session={session}>
            <Component {...pageProps} />
        </SessionProvider>
    </ChakraProvider>
  );
};

export default MyApp;
