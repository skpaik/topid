import {Box, Button,} from '@chakra-ui/react'
import {request} from 'graphql-request'
import axios from 'axios';
import {useState} from 'react'
import SkeletonCards from './skeleton'

//const fetcher = (url) => fetch(url).then((res) => res.json())
const fetcher = query => request('http://localhost:8080/graphql', query)
const timePeriods = ['Week', 'Month', 'Year', 'Latest']


const ProfileBooks2 = () => {
    const GRAPHCMS_PERMANENTAUTH_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6IlJPTEVfVVNFUiIsImp0aSI6IjNiNDEwMmFiNzdlMjRhMmRiNmFlNGRjNzY4OGRjYzBhIiwiaWF0IjoxNjc4NzM5MjEyLCJzdWIiOiJ1c2VyMTAyIiwiZXhwIjoxNjc4NzQyODEyfQ.viQM6pK3uZe26yH4AjifESkCM4RvuPEWhAtxu1Sj2MzIBscvvhKDaQwxsxRiQ2g9g6lCfJQRaTBHpFKNZfUZhA";
    const GRAPHCMS_URL = "http://localhost:8080/graphql";
    const [isActive, setIsActive] = useState(timePeriods[1])

    const [bookId, setBookId] = useState('book-1');
    const [userDetails, setUserDetails] = useState({});
    const [isLoading, setIsLoading] = useState(false);
    const [isError, setIsError] = useState();

    /*
    const { data, error } = useSWR(
        `{
              bookById(id: "book-2") {
                id
                name
                pageCount
                author {
                    id
                    firstName
                    lastName
                    }
                }
            }`,
        fetcher
    )
    */

    const getUserDetailByAxiosAPICall = async () => {
        try {
            /*
            if (!email) {
                return;
            }
            */
            setIsLoading(true);
            const headers = {
                'content-type': 'application/json',
                'Authorization': `Bearer ${GRAPHCMS_PERMANENTAUTH_TOKEN}`
            };
            /*
            const requestBody = {
                query: `query getNextUserByEmail($email:String!){
                    bookById(id: "book-2") {
                        id
                        name
                        pageCount
                        author {
                            id
                            firstName
                            lastName
                            }
                        }
                  }`,
                variables: { email }
            };
            */
            const requestBody = {
                query: `query bookDetails {
                          bookById(id: $bookId) {
                            id
                            name
                            pageCount
                            author {
                              id
                              firstName
                              lastName
                            }
                          }
                        }`,
                variables: {bookId}
            };

            console.log('headers____', headers);
            const options = {
                method: 'GET',
                url: GRAPHCMS_URL,
                headers,
                data: requestBody
            };

            const response = await axios(options);
            console.log('RESPONSE FROM AXIOS REQUEST', response.data);

            setUserDetails(response?.data?.data?.nextUser ?? {});
        } catch (err) {
            console.log('ERROR DURING AXIOS REQUEST', err);
        } finally {
            setIsLoading(false);
        }
    };


    if (isError) return <Box>failed to load</Box>
    if (isLoading)
        return (
            <Box mb="8" borderRadius="md">
                <SkeletonCards/>
            </Box>
        )

    return (
        <Box mb="8" borderRadius="md">
            <Button
                padding="0.4rem"
                width="auto"
                height="auto"
                borderRadius="100%"
                bg="transparent"
                _hover={{bg: "#f6f6f6"}}
                onClick={getUserDetailByAxiosAPICall}
            >
                Load GraphQl
            </Button>
            {/*{userDetails.map((post, idx) => (*/}
            {/*    <PostCard key={post.id} post_details={post} idx={idx}/>*/}
            {/*))}*/}
        </Box>
    )
}

export default ProfileBooks2
