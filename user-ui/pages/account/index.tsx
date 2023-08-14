import {Fragment, useState} from "react";
import {getSession, signIn, useSession} from "next-auth/react"
import {Box, Button, Grid, Text, VStack} from "@chakra-ui/react";
import Navbar from "../../components/layout/navbar";
import Sidebar from "../../components/layout/sidebar";
import Container from "../../components/layout/container";
import Listing from "../../components/listing";
import Profile from "../../components/account/profile";
import Meta from "../../components/layout/meta";
import styles from "../../components/css/header.module.css"

export default function AccountIndex() {
    const { data: session, status } = useSession()

    return (
        <Fragment>
            <Meta />
            <Navbar />
            <Box as="main" bg="#EEF0F1" id="page" mt="56px">
                <Container>
                    <Grid
                        templateColumns={{
                            base: "1fr",
                            md: "1fr 3fr",
                            lg: "1fr 3fr 1.5fr"
                        }}
                        d={{base: "block", md: 'grid'}}
                        gap={4}
                        pt="4"
                    >
                        <Sidebar d={{base: 'none', md: 'block' }} />
                        {!session && (
                            <VStack
                                align="flex-start"
                                spacing={0}
                                d={{ base: 'none', sm: 'flex' }}
                            >
                                <Text color="#4d5760" fontSize="14px" fontWeight="500">
                                    You are not signed in
                                </Text>
                                <Text color="#4d5760" fontSize="12px">
                                    <a
                                        href={`/api/auth/signin`}
                                        onClick={(e) => {
                                            e.preventDefault()
                                            signIn()
                                        }}                                    >
                                        Sign in
                                    </a>
                                </Text>
                            </VStack>
                        )}
                        {session?.user && (
                            <Profile logged_in_user={session.user} />
                        )}

                        <Listing d={{base: 'none', md: 'flex' }} />
                    </Grid>
                </Container>
            </Box>
        </Fragment>
    );
}
