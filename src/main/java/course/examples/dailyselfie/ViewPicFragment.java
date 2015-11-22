package course.examples.dailyselfie;

import android.app.Activity;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewPicFragment.OnLargePicListener} interface
 * to handle interaction events.
 * Use the {@link ViewPicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPicFragment extends Fragment {
    private static final String TAG= "ds-viewpicfragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PIC = "pic";

    // TODO: Rename and change types of parameters
    private String mPicPath;
//    private File mPic;

    private OnLargePicListener mListener;
    private ImageView mImageView;


//    // TODO: Rename and change types and number of parameters
    public static ViewPicFragment newInstance(String path) {
        ViewPicFragment fragment = new ViewPicFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PIC, path);
        fragment.setArguments(args);

        return fragment;
    }

    public ViewPicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mPicPath = getArguments().getString(ARG_PIC);

//            File[] images= MainActivity.SELFIE_DIR.listFiles();
//            for (File img : images) {
//                if (img.getName().equals(mPicName)) {
//                    mPic= img;
//                    break;
//                }
//            }
        }

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View largePicView= inflater.inflate(R.layout.fragment_view_pic, container, false);
        largePicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onLargePicClick();
                }
            }
        });

        mImageView= (ImageView) largePicView.findViewById(R.id.large_picture);
        mImageView.setImageURI(Uri.parse(mPicPath));

        return largePicView;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onLargePicClick();
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnLargePicListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnLargePicListener {
        // TODO: Update argument type and name
        public void onLargePicClick();
    }

}
