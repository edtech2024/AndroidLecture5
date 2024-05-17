package com.example.myapplicationfive


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainFragment : Fragment() {

    // Creates a new fragment given parameters
    // ListFragment.newInstance()
    companion object {

        private const val TYPE_1 = "Type 1"
        private const val TYPE_2 = "Type 2"

        fun newInstance(type1: String?, type2: String?): MainFragment {
            val fragmentMain = MainFragment()
            val args = Bundle()
            args.putString(TYPE_1, type1)
            args.putString(TYPE_2, type2)

            fragmentMain.arguments = args
            return fragmentMain
        }
    }

    // Define the events that the fragment will use to communicate
    interface OnItemAddListener{
        // This can be any number of events to be sent to the activity
        fun onAddItem(add: Bundle)
    }

    // Define the listener of the interface type
    // listener will the activity instance containing fragment
    private var listenerAdd: OnItemAddListener? = null

    private var argsType1: String? = null
    private var argsType2: String? = null

    // When requested, this adapter returns a ObjectFragment,
    // representing an object in the collection.
    private lateinit var pageAdapter: FragmentListStateAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var addFab: FloatingActionButton

    lateinit var bsdFragment: BSDFragment
    lateinit var btnOpen: Button

    var flag: Boolean = false

    lateinit var listViewModel: ListViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listenerAdd = context as MainFragment.OnItemAddListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        argsType1 = getArguments()?.getString(MainFragment.TYPE_1, "Type 1")
        argsType2 = getArguments()?.getString(MainFragment.TYPE_2, "Type 2")

        listViewModel = ViewModelProvider(this , object: ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ListViewModel(ItemRepository) as T
            }
        }
        ).get(ListViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializationFAB(view)
        initializationViewPager(view)
        initializationBSD(view)
    }

    override fun onDetach() {
        super.onDetach()
        listenerAdd = null
    }

    private fun initializationViewPager(view: View){
        viewPager = view.findViewById(R.id.view_pager2)
        pageAdapter = FragmentListStateAdapter(this, transferBundle())
        viewPager.adapter = pageAdapter

        tabLayout = view.findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = "Type ${(position + 1)}"
            }
        ).attach()
    }


    private fun transferBundle():Bundle {
        val args = Bundle()
        args.putString(TYPE_1, argsType1)
        args.putString(TYPE_2, argsType2)
        return args
    }


    private fun initializationFAB(view: View){
        // Register the FABs with ID
        addFab = view.findViewById(R.id.fab)
        // do something when the button is clicked
        addFab.setOnClickListener {
            // Passing the data to the DetailFragment
            val args = Bundle()
            args.putString("Action", "Create")

            onAddClicked(args)
        }
    }

    // Now we can fire the event when the user selects something in the fragment
    fun onAddClicked(bundle: Bundle){
        listenerAdd?.onAddItem(bundle)
    }


    fun initializationBSD(view: View) {
        btnOpen = view.findViewById(R.id.btnOpen)
        var flagOpen: Boolean = false

        btnOpen.setOnClickListener() {
            if (flagOpen != true) {
                flagOpen = true
                btnOpen.text = "Close"
                openBSDFragment()
            }
            else {
                flagOpen = false
                btnOpen.text = "Open"
                closeBSDFragment()
            }
        }
    }

    fun openBSDFragment(){
        if (flag != true) {
            flag = true
            bsdFragment = BSDFragment.newInstance(argsType1)
            val fragmentTransaction: FragmentTransaction = childFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container_bsd, bsdFragment)
            fragmentTransaction.commit()
        } else {
            flag = false
            bsdFragment = BSDFragment.newInstance(argsType2)
            val fragmentTransaction: FragmentTransaction = childFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container_bsd, bsdFragment)
            fragmentTransaction.commit()
        }
    }

    fun closeBSDFragment(){
        bsdFragment.dismiss()
    }

}
