import {Box, Button, Flex, Heading, Image, Link, Spacer, Text} from "@chakra-ui/react";
import {ReactNode} from "react";

type LinkButtonProps = {
    children: ReactNode;
};

const LinkButton = ({children}: LinkButtonProps) => {
    return (
        <Button
            _hover={{color: "#323ebe", bg: "#e2e4e6"}}
            bg="transparent"
            width="14rem"
            padding="8px"
            fontWeight="normal"
            justifyContent="flex-start"
        >
            {children}
        </Button>
    );
};

const Account = () => {
    return (
        <Box as="nav">
            <Flex pl="2" py="4">
                <Heading as="h3" fontSize="1rem">
                    Account
                </Heading>
                <Spacer/>
                <Image src="/assets/images/settings.svg"/>
            </Flex>
            <LinkButton>
                <Image src="/assets/images/sidebar/home.svg" mr="3"/>
                Create Account
            </LinkButton>
            <LinkButton>
                <Image src="/assets/images/sidebar/reading.svg" mr="3"/>
                Login
            </LinkButton>
        </Box>
    );
};
const Links = () => {
    return (
        <Box as="nav">
            <Flex pl="2" py="4">
                <Heading as="h3" fontSize="1rem">
                    Menu
                </Heading>
                <Spacer/>
                <Image src="/assets/images/settings.svg"/>
            </Flex>
            <LinkButton>
                <Image src="/assets/images/sidebar/home.svg" mr="3"/>
                <Link
                    href='/'
                    _hover={{color: '#323ebe', textDecoration: 'none'}}>
                    Home
                </Link>
            </LinkButton>
            <LinkButton>
                <Image src="/assets/images/sidebar/reading.svg" mr="3"/>
                Posts
            </LinkButton>
            <LinkButton>
                <Image src="/assets/images/sidebar/listing.svg" mr="3"/>
                Offer
            </LinkButton>
            <LinkButton>
                <Image src="/assets/images/sidebar/video.svg" mr="3"/>
                <Link
                    href='/group'
                    _hover={{color: '#323ebe', textDecoration: 'none'}}>
                    Group
                </Link>
            </LinkButton>
            <LinkButton>
                <Image src="/assets/images/sidebar/video.svg" mr="3"/>
                <Link
                    href='/profiles'
                    _hover={{color: '#323ebe', textDecoration: 'none'}}>
                    Profiles
                </Link>
            </LinkButton>
            <LinkButton>
                <Image src="/assets/images/sidebar/podcast.svg" mr="3"/>
                Info/Listings
            </LinkButton>
            <LinkButton>
                <Image src="/assets/images/sidebar/tag.svg" mr="3"/>
                Guides
            </LinkButton>
            <LinkButton>
                <Image src="/assets/images/sidebar/tag.svg" mr="3"/>
                Micro Site
            </LinkButton>
            <LinkButton>
                <Text fontWeight="normal" color="#4d5760" ml="2.3rem">
                    More...
                </Text>
            </LinkButton>
        </Box>
    );
};

const TagList = ({children}) => {
    return (
        <Box>
            {children &&
                children.map((item, idx) => <LinkButton key={idx}>#{item}</LinkButton>)}
        </Box>
    );
};

const Tags = () => {
    return (
        <Box mt="6">
            <Flex pl="2" py="4">
                <Heading as="h3" fontSize="1rem">
                    My Tags
                </Heading>
                <Spacer/>
                <Image src="/assets/images/settings.svg"/>
            </Flex>
            <Box maxH="50vh" overflowY="auto">
                <TagList>
                    {[
                        "Nextjs",
                        "react",
                        "javascript",
                        "ruby",
                        "ruby on rails",
                        "beginners",
                        "typescript"
                    ]}
                </TagList>
            </Box>
        </Box>
    );
};

const Sidebar = props => {
    return (
        <Box as="aside" {...props}>
            <Account/>
            <Links/>
            <Tags/>
        </Box>
    );
};

export default Sidebar;
