void sort( )
{
	int i, j, t,n;
            n=4;
            while(n<2)
            {
             	n++;
            }
	if ( n <2 ) 
	return;
	for( i=0 ; i < n-1; i++) 
	{
		for( j=i+ 1 ; j < n ; j++)
		{
			if ( a[i] > a[ j]  )
			{
				t = a[ i] ;
				a[ i] =a[ j] ;
				a[ j]  = t;
			}
		}
	}
            return;
}
