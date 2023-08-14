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
    Wrap
} from '@chakra-ui/react'
import {css} from '@emotion/react'
import styled from '@emotion/styled'
import useSWR from 'swr'
import SkeletonCards from './skeleton'
import PostCard from '../shared/post-card'

styled(Button)`
  position: relative;
  padding: 0.4rem 0.5rem;
  font-weight: normal;

  &:hover {
    color: #3b49df;

    &::after {
      width: 100%;
    }
  }

  &:focus {
    box-shadow: none;
  }

  ${(props) =>
          props.isCurrent &&
          css`
            font-weight: 500;

            &::after {
              transition: width 0.2s ease;
              position: absolute;
              bottom: 0;
              margin: auto;
              content: '';
              height: 3px;
              width: 70%;
              border-radius: 4px;
              background-color: #3b49df;
            }
          `}
`;

const Header = () => {
    return (
        <Box as="header">
            <HStack spacing=".6rem">
                <Heading fontSize="1.25rem">PostDetails</Heading>
                <Spacer/>
            </HStack>
        </Box>
    )
}

const fetcher = (url) => fetch(url).then((res) => res.json())

const PostDetails = ({postId}) => {
    const {data, error} = useSWR(
        `https://dev.to/api/articles/${postId}`,
        fetcher
    )

    if (error) return <Box>failed to load</Box>
    if (!data)
        return (
            <Box mb="8" borderRadius="md">
                <SkeletonCards/>
            </Box>
        )
    return (
        <Box mb="8" borderRadius="md">
            <PostCard post_details={data}/>
        </Box>
    )
}

export default PostDetails
