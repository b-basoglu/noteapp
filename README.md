#

#Technologies and Terminologies

    MVVM architecture

    Live Data
    
    Hilt
    
    Kotlin

    Paging3
    
    Binding
    
    Coroutines
  
    Navigation Component
    
    JUnit4
    
    Expresso
    
    Picasso

#Structure (Implementation and decision explained in comments)

    MainActivity
        Hosts fragment container and toolbar
    
    MainBaseFragment
        Handles interaction between activity and fragments
        
    MainFragment
        Observe user interaction and lists notes
        
    EditNoteFragment
        Observe user interaction and add delete and update notes
        
    MainViewModel
        Gets notes
    
    MainRepository
        Perform CRUD operations on local database that stores noteDao
        
    AppModule
        Singleton app context provider
        
    AppModule
        Singleton app context provider

    NoteListAdapter
        PagedListAdapter for recyclerview
        
    2 custom view CircleProgressBar and NoteImageView
    
    FragmentUtils implemented for fragment transaction // Do not used since navigation component added

#
