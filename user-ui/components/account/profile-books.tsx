import {Box, Button,} from '@chakra-ui/react'
import {gql, GraphQLClient, request} from 'graphql-request'
import {HYGRAPH_PERMANENTAUTH_TOKEN, HYGRAPH_URL} from '../../lib/constants';
import {useState, useEffect} from 'react'
import SkeletonCards from './skeleton'

//const fetcher = (url) => fetch(url).then((res) => res.json())
const fetcher = query => request('http://localhost:8080/graphql', query)
const timePeriods = ['Week', 'Month', 'Year', 'Latest']

const client = new GraphQLClient(HYGRAPH_URL, {
    headers: {
        Authorization: `Bearer ${HYGRAPH_PERMANENTAUTH_TOKEN}`,
        Accept: 'application/json',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
    },
});

const query = gql`
    query bookDetails {
        bookById(id: "book-1") {
            id
            name
            pageCount
            author {
                id
                firstName
                lastName
            }
        }
    }
`;

const ProfileBooks = () => {
    // const GRAPHCMS_PERMANENTAUTH_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6IlJPTEVfVVNFUiIsImp0aSI6IjNiNDEwMmFiNzdlMjRhMmRiNmFlNGRjNzY4OGRjYzBhIiwiaWF0IjoxNjc4NzM5MjEyLCJzdWIiOiJ1c2VyMTAyIiwiZXhwIjoxNjc4NzQyODEyfQ.viQM6pK3uZe26yH4AjifESkCM4RvuPEWhAtxu1Sj2MzIBscvvhKDaQwxsxRiQ2g9g6lCfJQRaTBHpFKNZfUZhA";
    // const GRAPHCMS_URL = "http://localhost:8080/graphql";
    const [isActive, setIsActive] = useState(timePeriods[1])

    const [bookId, setBookId] = useState('book-1');
    const [userDetails, setUserDetails] = useState({});
    const [isLoading, setIsLoading] = useState(false);
    const [isError, setIsError] = useState();
    useEffect(() => {
        //setLoading(true)

    }, [])

    const getUserDetailByGraphQLRequestAPICall = async () => {
        try {
            const variables = {  };
            const response = await client.request(query, variables);
            console.log('RESPONSE FROM GRAPHQL-REQUEST API CALL', response);
        } catch (err) {
            console.log('ERROR FROM GRAPHQL-REQUEST API CALL', err);
        } finally {
            setIsLoading(false);
        }
    };
    const loadTutorialsAxios = async () => {
        try {
            //http://localhost:8080/api/tutorials
            /*
            const res = await fetch(`http://localhost:8080/api/tutorials/`);
            const data = await res.json();
            console.log(data);
            */


            fetch('http://localhost:8080/api/tutorials')
                .then((res) => res.json())
                .then((data) => {
                    console.log('RESPONSE FROM GRAPHQL-REQUEST API CALL', data);
                })
                .catch(rejected => {
                    console.log(rejected);
                });
        } catch (err) {
            console.log('ERROR FROM GRAPHQL-REQUEST API CALL', err);
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
                onClick={getUserDetailByGraphQLRequestAPICall}
            >
                Load GraphQl
            </Button>
            <Button
                padding="0.4rem"
                width="auto"
                height="auto"
                borderRadius="100%"
                bg="transparent"
                _hover={{bg: "#f6f6f6"}}
                onClick={loadTutorialsAxios}
            >
                Load Axios
            </Button>
            {/*{userDetails.map((post, idx) => (*/}
            {/*    <PostCard key={post.id} post_details={post} idx={idx}/>*/}
            {/*))}*/}
        </Box>
    )
}

export default ProfileBooks
