import {
    Box,
    Heading,
    Spacer,
    Button,
    VStack,
    HStack,
    Grid,
    Text,
    Link,
    Image,
    Select,
    Tabs,
    TabList,
    Tab,
    TabPanels,
    TabPanel
} from '@chakra-ui/react'
import { css } from '@emotion/react'
import styled from '@emotion/styled'

import { useState } from 'react'
import ProfilePosts from "./profile-posts";
import ProfileBooks from "./profile-books";

const Header = ({ isActive, setIsActive, user_profile }) => {
    return (
        <Box as="header">
            <HStack spacing=".6rem">
                <Image
                    alt="user profile"
                    src={'https://yt3.googleusercontent.com/4w7K_UVI7OEwDyfmmACmvKWrKKG6hUbNr2LcYyohytOk3o88yhYL89BRcDXPEXXJkdFFCeW5=w1060-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj'}
                    w="100%"
                />
            </HStack>
            <HStack>
                <Image
                    alt="user profile"
                    src={user_profile.image}
                    w="8"
                    borderRadius="full"
                />
                <Heading fontSize="1.25rem">
                    {user_profile.name}
                </Heading>
                <Spacer />
                <Link href={'/profile/edit'}>Edit profile</Link>
            </HStack>
        </Box>
    )
}


const timeperiods = ['All', 'My group']

const Profile = ({logged_in_user}) => {
    const [isActive, setIsActive] = useState(timeperiods[1])
    return (
        <Box mb="8" borderRadius="md">
            <Header isActive={isActive} setIsActive={setIsActive} user_profile={logged_in_user}/>

            <Tabs isFitted variant="enclosed">
                <TabList mb="1em">
                    <Tab>Home</Tab>
                    <Tab>Books</Tab>
                    <Tab>Posts</Tab>
                </TabList>
                <TabPanels>
                    <TabPanel>
                        <ProfileBooks></ProfileBooks>
                    </TabPanel>
                    <TabPanel>
                        <pre>{JSON.stringify(logged_in_user, null, 2)}</pre>
                    </TabPanel>
                    <TabPanel>
                        <ProfilePosts></ProfilePosts>
                    </TabPanel>
                </TabPanels>
            </Tabs>
        </Box>
    )
}

export default Profile
