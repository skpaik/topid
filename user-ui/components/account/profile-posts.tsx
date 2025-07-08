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
} from '@chakra-ui/react'
import { css } from '@emotion/react'
import styled from '@emotion/styled'
import useSWR from 'swr'
import { useState } from 'react'
import SkeletonCards from './skeleton'
import PostCard from "../shared/post-card";

const fetcher = (url) => fetch(url).then((res) => res.json())

const timeperiods = ['Week', 'Month', 'Year', 'Latest']
function returnFetchUrl(isActive) {
    if (isActive === 'Week') {
        return ''
    }
    return isActive.toLowerCase()
}

const ProfilePosts = () => {
    const [isActive, setIsActive] = useState(timeperiods[1])
    const { data, error } = useSWR(
        `https://dev.to/stories/feed/${returnFetchUrl(isActive)}?page=1`,
        fetcher
    )

    if (error) return <Box>failed to load</Box>
    if (!data)
        return (
            <Box mb="8" borderRadius="md">
                <SkeletonCards />
            </Box>
        )

    return (
        <Box mb="8" borderRadius="md">
            {data.map((post, idx) => (
                <PostCard key={post.id} post_details={post} idx={idx}/>
            ))}
        </Box>
    )
}

export default ProfilePosts
