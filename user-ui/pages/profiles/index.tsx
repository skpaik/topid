import {Fragment} from "react";
import {signIn, useSession} from "next-auth/react"
import {Box, Grid, GridItem, Text, VStack} from "@chakra-ui/react";
import Navbar from "../../components/layout/navbar";
import Sidebar from "../../components/layout/sidebar";
import Container from "../../components/layout/container";
import Listing from "../../components/listing";
import Profile from "../../components/account/profile";
import Meta from "../../components/layout/meta";



import { Box, Code, Text, Link, VStack, Grid } from "@chakra-ui/react"
import { Chakra } from "../Chakra"
import { Layout } from "../components/Layout"
import { Logo } from "../components/Logo"
import { NextChakraLink } from "../components/NextChakraLink"
import { Suspense } from "react"

interface IndexProps {
    cookies?: string
}

const IndexPage = ({ cookies }: IndexProps) => (
    <Chakra cookies={cookies}>
        <Layout title="Next.js + TypeScript example">
            <Suspense fallback="Loading...">
                <Box textAlign="center" fontSize="xl">
                    <Grid minH="100vh" p={3}>
                        <VStack spacing={8}>
                            <Logo h="40vmin" pointerEvents="none" />
                            <Text>
                                Edit <Code fontSize="xl">pages/index.tsx</Code> and save to
                                reload.
                                <br />
                                <br />
                                <NextChakraLink href="/properties" color="teal.500">
                                    View the properties
                                </NextChakraLink>{" "}
                                to see the Nextjs <Code fontSize="xl">&lt;Link&gt;</Code> in
                                action
                            </Text>
                            <Link
                                color="teal.500"
                                fontSize="2xl"
                                href="https://chakra-ui.com"
                                target="_blank"
                                rel="noopener noreferrer"
                            >
                                Learn more about Chakra
                            </Link>
                        </VStack>
                    </Grid>
                </Box>
            </Suspense>
        </Layout>
    </Chakra>
)

export default IndexPage
export { getServerSideProps } from "../Chakra"