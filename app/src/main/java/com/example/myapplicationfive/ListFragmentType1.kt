package com.example.myapplicationfive

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationfive.databinding.FragmentListT1Binding

class ListFragmentType1 : Fragment() {

    // Creates a new fragment given parameters
    // ListFragmentType1.newInstance()
    companion object {

        private const val TYPE = "Type"

        fun newInstance(type: String?): ListFragmentType1 {
            val fragmentListT1 = ListFragmentType1()
            val args = Bundle()
            args.putString(TYPE, type)
            fragmentListT1.arguments = args
            return fragmentListT1
        }
    }

    // Define the events that the fragment will use to communicate
    interface OnItemEditType1Listener{
        // This can be any number of events to be sent to the activity
        fun onEditItemType1(edit: Bundle)
    }

    // Define the listener of the interface type
    // listener will the activity instance containing fragment
    private var listenerEdit: OnItemEditType1Listener? = null

    private lateinit var itemAdapterType1: ItemAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listenerEdit = context as ListFragmentType1.OnItemEditType1Listener
    }

    val listViewModel: ListViewModel by viewModels( ownerProducer = { requireParentFragment() })

    private var _binding: FragmentListT1Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentListT1Binding.inflate( inflater, container, false)
        _binding!!.lifecycleOwner = this
        _binding!!.listViewModel = listViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvListType1.layoutManager = LinearLayoutManager(this.context)
        this.itemAdapterType1 = ItemAdapter(){ item -> onItemClicked(item) }
        binding.rvListType1.adapter = itemAdapterType1
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDetach() {
        super.onDetach()
        listenerEdit  = null
    }

    // Now we can fire the event when the user selects something in the fragment
    private fun onEditClicked(bundle: Bundle){
        listenerEdit?.onEditItemType1(bundle)
    }

    private fun onItemClicked(item: Item){

        val args = Bundle()
        args.putString("Action", "Edit")
        args.putInt("Id", item.id!!)
        args.putString("Title", item.title)
        args.putString("Description", item.description)
        args.putString("Priority", item.priority!!.toString())
        args.putString("Type", item.type)
        args.putString("Count", item.count!!.toString())
        args.putString("Period", item.period!!.toString())

        onEditClicked(args)
    }

}
