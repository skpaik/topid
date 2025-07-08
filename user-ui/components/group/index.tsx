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
    Select
} from '@chakra-ui/react'
import {css} from '@emotion/react'
import styled from '@emotion/styled'
import useSWR from 'swr'
import {useState} from 'react'
import SkeletonCards from './skeleton'
import PostCard from "../shared/post-card";

const HeaderBtn = styled(Button)`
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
`

const Header = ({isActive, setIsActive}) => {
    return (
        <Box as="header">
            <HStack spacing=".6rem">
                <Heading fontSize="1.25rem">Group</Heading>
                <Spacer/>
                <Select placeholder='Any country'>
                    <option value='option1'>Bangladesh</option>
                    <option value='option2'>India</option>
                    <option value='option3'>Pakistan</option>
                </Select>
                <Select placeholder='Any city'>
                    <option value='option1'>Dhaka</option>
                    <option value='option2'>Chittagong</option>
                    <option value='option3'>Khulna</option>
                </Select>
                {timeperiods.map((item, idx) => {
                    if (isActive === item) {
                        return (
                            <HeaderBtn
                                key={idx}
                                isCurrent={isActive}
                                onClick={() => setIsActive(item)}
                            >
                                {item}
                            </HeaderBtn>
                        )
                    }
                    return (
                        <HeaderBtn key={idx} onClick={() => setIsActive(item)}>
                            {item}
                        </HeaderBtn>
                    )
                })}
            </HStack>
        </Box>
    )
}

const fetcher = (url) => fetch(url).then((res) => res.json())

const timeperiods = ['All', 'My group']

function returnFetchUrl(isActive) {
    if (isActive === 'All') {
        return ''
    }
    return isActive.toLowerCase()
}

const GroupIndex = () => {
    const [isActive, setIsActive] = useState(timeperiods[1])
    const {data, error} = useSWR(
        `https://dev.to/stories/feed/${returnFetchUrl(isActive)}?page=1`,
        fetcher
    )

    if (error) return <Box>failed to load</Box>
    if (!data)
        return (
            <Box mb="8" borderRadius="md">
                <Header isActive={isActive} setIsActive={setIsActive}/>
                <SkeletonCards/>
            </Box>
        )

    return (
        <Box mb="8" borderRadius="md">
            <Header isActive={isActive} setIsActive={setIsActive}/>
            {data.map((post, idx) => (
                <PostCard key={post.id} post_details={post} idx={idx}/>
            ))}
        </Box>
    )
}

export default GroupIndex
