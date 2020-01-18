package com.icosom.social.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.icosom.social.R;
import com.icosom.social.activity.AddFeedActivity;
import com.icosom.social.model.FeedModel;
import com.icosom.social.model.LikeDislikeModel;
import com.icosom.social.model.TagModel;
import com.icosom.social.recycler_adapter.HomeRecyclerAdapter;
import com.icosom.social.utility.AppController;
import com.icosom.social.utility.CommonFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    ArrayList<FeedModel> result = new ArrayList<FeedModel>();
    Set<String> titles = new HashSet<String>();
    RecyclerView rv_home;
    RecyclerView.LayoutManager layoutManager;
    HomeRecyclerAdapter recyclerAdapter;
    // AppController appController;
    SwipeRefreshLayout srl_home;
    boolean isLoading = false;
    boolean firstTime = true;
    int counting = 0;
    ArrayList<FeedModel> modelArrayList;
    ArrayList<Boolean> showFooter;
    BottomSheetDialog sheetDialogMe, sheetDialogOther;
    int position, check = 0;
    TextView text_home;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    String url;
    FloatingActionButton fab;

    AppController appController;
    Boolean isImage = true;
    String imageFilePath;
    String post_link = "";
    private String mCurrentPhotoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        appController = (AppController) getContext().getApplicationContext();


        fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddFeedActivity.class));
            }
        });

        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        edt = sp.edit();
        //  userInfo(sp.getString("userId", ""));
        url = CommonFunctions.FETCH_IMAGES + sp.getString("profile", "");
        text_home = v.findViewById(R.id.text_home);
        srl_home = v.findViewById(R.id.srl_home);
        rv_home = v.findViewById(R.id.rv_home);
        sheetDialogMe = new BottomSheetDialog(getActivity());
        sheetDialogOther = new BottomSheetDialog(getActivity());

        modelArrayList = new ArrayList<>();
        showFooter = new ArrayList<>();

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_home.setLayoutManager(layoutManager);

        srl_home.setDistanceToTriggerSync(100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            srl_home.setProgressViewOffset(false, 0, 300);
        }

        srl_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                try {
                    counting = 0;
                    // showFooter.set(0, false);
                    updateToken();
                    loadData(counting);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("on_Refresh_Exception_Found_" + e);
                }


            }
        });

        srl_home.setRefreshing(true);


        if (CommonFunctions.app_running == false) {
            System.out.println("app_running_if" + CommonFunctions.app_running);
            loadData(counting);
        } else {
            text_home.setVisibility(View.GONE);
            System.out.println("app_running_else" + CommonFunctions.app_running);
            srl_home.setRefreshing(false);
            recyclerAdapter = new HomeRecyclerAdapter(getContext(), CommonFunctions.home_page_list, showFooter, HomeFragment.this, url);
            rv_home.setAdapter(recyclerAdapter);
            recyclerAdapter.notifyDataSetChanged();
        }


        rv_home.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

                if (lastVisibleItem == totalItemCount - 1 && !isLoading && !firstTime) {
                    //   Toast.makeText(getContext(), "counting: "+counting, Toast.LENGTH_SHORT).show();
                    isLoading = true;


                    if (modelArrayList.size() == 0)
                        counting = 0;


                    updateToken();
                    loadData(counting);

                }
            }
        });

        View sheetViewMe = LayoutInflater.from(getActivity()).inflate(R.layout.more_options_sheet_layout_me, null);
        View sheetViewOther = LayoutInflater.from(getActivity()).inflate(R.layout.more_options_sheet_layout_other, null);
        sheetDialogMe.setContentView(sheetViewMe);
        sheetDialogOther.setContentView(sheetViewOther);

        TextView txt_edtPostMe = sheetViewMe.findViewById(R.id.txt_edtPostMe);
        TextView txt_hidePostMe = sheetViewMe.findViewById(R.id.txt_hidePostMe);
        TextView txt_deletePostMe = sheetViewMe.findViewById(R.id.txt_deletePostMe);
        TextView txt_sharelinkMe = sheetViewMe.findViewById(R.id.share_link);

        TextView txt_savePostOther = sheetViewOther.findViewById(R.id.txt_savePostOther);
        TextView txt_hidePostOther = sheetViewOther.findViewById(R.id.txt_hidePostOther);
        TextView txt_reportPostOther = sheetViewOther.findViewById(R.id.txt_reportPostOther);
        TextView txt_share_linkOther = sheetViewOther.findViewById(R.id.share_link);

        txt_edtPostMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getContext(), AddFeedActivity.class).
                        putExtra("share", true).
                        putExtra("edit", true).
                        putExtra("position", position).
                        putExtra("postId", modelArrayList.get(position).getPostId()).
                        putExtra("name", modelArrayList.get(position).getPostUserFirstName() + " " +
                                modelArrayList.get(position).getPostUserLastName()).
                        putExtra("desc", modelArrayList.get(position).getPostDescription()), 30);
                sheetDialogMe.hide();
            }
        });


        txt_sharelinkMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("me_link" + post_link);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                //String shareBodyText = "Your shearing message goes here";
                intent.putExtra(Intent.EXTRA_SUBJECT, "Icosom ");
                intent.putExtra(Intent.EXTRA_TEXT, post_link);
                startActivity(Intent.createChooser(intent, "Choose sharing method"));

            }
        });

        txt_share_linkOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("me_other_link" + post_link);


                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                //String shareBodyText = "Your shearing message goes here";
                intent.putExtra(Intent.EXTRA_SUBJECT, "Icosom ");
                intent.putExtra(Intent.EXTRA_TEXT, post_link);
                startActivity(Intent.createChooser(intent, "Choose sharing method"));

            }
        });


        txt_hidePostMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                modelArrayList.remove(position);
                recyclerAdapter.notifyDataSetChanged();
                sheetDialogMe.hide();
            }
        });

        txt_deletePostMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePost();
                sheetDialogMe.hide();
            }
        });

        txt_savePostOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetDialogOther.hide();
            }
        });

        txt_hidePostOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelArrayList.remove(position);
                recyclerAdapter.notifyDataSetChanged();
                sheetDialogOther.hide();
            }
        });

        txt_reportPostOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetDialogOther.hide();
            }
        });


        return v;
    }


  /*  public void openCamera() {
        openCameraIntent();

    }

    private void openCameraIntent()
    {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("Camer_Kiaf_exception", "IOException");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Context ctx = getContext();
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, 2);
            }
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        // mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        mCurrentPhotoPath =image.getAbsolutePath();
        //imageFilePath = image.getAbsolutePath();
        imageFilePath = mCurrentPhotoPath;
        return image;
    }
*/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == RESULT_OK) {
            // comment activity
            if (data.getBooleanExtra("isComment", false)) {
                int pos = data.getIntExtra("position", 0);
                modelArrayList.get(pos).setMyComment(true);
                modelArrayList.get(pos).setPostTotalComments((Integer.parseInt(modelArrayList.get(pos).getPostTotalComments()) + 1) + "");
                recyclerAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == 20 && resultCode == RESULT_OK) {
            // add post
            counting = 0;
            modelArrayList.clear();
            showFooter.set(0, false);
            loadData(counting);
        } else if (requestCode == 30 && resultCode == RESULT_OK) {
            // edit post
            if (data.getIntExtra("position", -1) != -1) {
                modelArrayList.get(data.getIntExtra("position", 0)).
                        setPostDescription(data.getStringExtra("description"));
                recyclerAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == 40 && resultCode == RESULT_OK) {
            counting = 0;
            modelArrayList.clear();
            showFooter.set(0, false);
            loadData(counting);
        }
    }

    public void showSheetOptions(int pos, String post_link) {
        position = pos;
        this.post_link = post_link;

        System.out.println("showsheet_position" + pos + " " + modelArrayList.size());

        if (modelArrayList.size() == 0) {
            if (sp.getString("userId", "").equals(CommonFunctions.home_page_list.get(position).getPostUserId())) {
                sheetDialogMe.show();
            } else {
                sheetDialogOther.show();
            }
        } else {
            if (sp.getString("userId", "").equals(modelArrayList.get(position).getPostUserId())) {
                sheetDialogMe.show();
            } else {
                sheetDialogOther.show();
            }

        }
    }

    private void deletePost() {
        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", sp.getString("userId", "")).
                add("postId", modelArrayList.get(position).getPostId()).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.DELETE_POST).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jo = new JSONObject(myResponse);
                            if (jo.getString("status").equals("1")) {
                                counting = 0;
                                modelArrayList.clear();
                                showFooter.set(0, false);
                                loadData(counting);
                            } else {
                                Toast.makeText(getContext(), "" + jo.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                // nothing to do
            }
        });
    }

     /* private void loadData(final int count) {
          System.out.println("load Data: " + count);

        String key_a = "userId";
        String value_a = sp.getString("userId", "");
        String key_b = "data-source";
        String value_b = "android";
        String key_c = "count";
        String value_c = count+"";
        String url = CommonFunctions.FETCH_FEEDS;

        RequestBody body = RequestBuilder.threeParameter(key_a, value_a, key_b, value_b, key_c, value_c);

          try {
              AppController.getInstance().PostTest(url, body, new GetLastIdCallback() {
                  @Override
                  public void lastId(String response) {
                      System.out.println("load Data: " + response);

                      {
                          if (srl_home.isRefreshing())
                              if (getActivity() != null)
                              {
                                  getActivity().runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          if (recyclerAdapter != null)
                                          {
                                              rv_home.getRecycledViewPool().clear();
                                              recyclerAdapter.notifyDataSetChanged();
                                          }
                                          modelArrayList.clear();
                                          srl_home.setRefreshing(false);
                                      }
                                  });
                              }

                          String myResponse = response;
                          System.out.println("hhhhhhhhhhhhhhhhh "+myResponse);
                          try
                          {
                              JSONArray ja = new JSONArray(myResponse);
                              if (ja.length() != 0)
                              {
                                  for (int i = 0; i < ja.length(); i++)
                                  {
                                      JSONObject jo = ja.getJSONObject(i);

                                      JSONObject post = jo.getJSONObject("post");
                                      JSONObject action = jo.getJSONObject("action");
                                      JSONObject share = null;
                                      JSONObject tag = null;

                                      if (post.getString("shareFlag").equalsIgnoreCase("1"))
                                      {
                                          share = jo.getJSONObject("share");
                                      }

                                      if (post.getString("tagFlag").equalsIgnoreCase("1"))
                                      {
                                          tag = jo.getJSONObject("tag");
                                      }

                                      String postId = post.getString("id");
                                      String postType = post.getString("type");
                                      String postUserId = post.getString("userId");
                                      String postTotalLikes = post.getString("totalLikes");
                                      String postTotalDislikes = post.getString("totalDislikes");
                                      String postTotalComments = post.getString("totalComments");
                                      String postTotalShares = post.getString("totalShares");
                                      String postDescription = post.getString("description").trim();
                                      String postImgFlag = post.getString("flag");
                                      String postLink = post.getString("fileLink");
                                      ArrayList<String> postFileLinks = new ArrayList<>();
                                      for (String str : postLink.split(","))
                                      {
                                          postFileLinks.add(str);
                                      }
                                      if (postFileLinks.size()>0 && postFileLinks.get(0).equalsIgnoreCase(""))
                                      {
                                          postFileLinks.remove(0);
                                      }
                                      Boolean isShared = post.getString("shareFlag").equals("1");
                                      String postTime = post.getString("postTime");
                                      String postUserFirstName = post.getString("firstName");
                                      String postUserLastName = post.getString("lastName");
                                      String postUserProfilePicture = post.getString("profilePicture");
                                      Boolean postIsTagged = post.getString("tagFlag").equals("1");
                                      String postUserGender = post.getString("gender");

                                      Boolean isMyLike = action.getString("like").equals("1");
                                      Boolean isMyDislike = action.getString("dislike").equals("1");
                                      Boolean isMyComment = action.getString("comment").equals("1");
                                      Boolean isMyShare = action.getString("share").equals("1");

                                      String sharePostId = share != null?share.getString("id"):"";
                                      String sharePostUserId = share != null?share.getString("userId"):"";
                                      String sharePostDescription = share != null?share.getString("description").trim():"";
                                      String sharePostImgFlag = share != null?share.getString("flag"):"";
                                      String sharePostlink = share != null?share.getString("fileLink"):"";
                                      ArrayList<String> shareFileLinks = new ArrayList<>();
                                      for (String str : sharePostlink.split(","))
                                      {
                                          shareFileLinks.add(str);
                                      }
                                      if (shareFileLinks.size()>0 && shareFileLinks.get(0).equalsIgnoreCase(""))
                                      {
                                          shareFileLinks.remove(0);
                                      }
                                      String sharePostTime = share != null?share.getString("postTime"):"";
                                      String sharePostUserFirstName = share != null?share.getString("firstName"):"";
                                      String sharePostUserLastName = share != null?share.getString("lastName"):"";
                                      String sharePostUserProfilePicture = share != null?share.getString("profilePicture"):"";
                                      String sharePostUserGender = share != null?share.getString("gender"):"";

                                      String tagCount = tag!=null?tag.getString("tagCount"):"0";
                                      ArrayList<TagModel> tagModels = new ArrayList<>();;
                                      if (tag != null)
                                      {
                                          JSONArray jaTag = tag.getJSONArray("tagData");
                                          for (int j = 0; j < jaTag.length(); j++)
                                          {
                                              JSONObject joTag = jaTag.getJSONObject(j);

                                              String tagFirstName = joTag.getString("firstName");
                                              String tagLastName = joTag.getString("lastName");
                                              String tagId = joTag.getString("id");

                                              tagModels.add(new TagModel(tagFirstName, tagLastName, tagId));
                                          }
                                      }
                                      modelArrayList.add(new FeedModel(
                                              postId, postType, postUserId, postTotalLikes, postTotalDislikes,
                                              postTotalComments, postTotalShares, postDescription, postImgFlag,
                                              postFileLinks, isShared, postTime, postUserFirstName, postUserLastName,
                                              postUserProfilePicture, postIsTagged, postUserGender, isMyLike, isMyDislike,
                                              isMyComment, isMyShare, sharePostId, sharePostUserId, sharePostDescription,
                                              sharePostImgFlag, shareFileLinks, sharePostTime, sharePostUserFirstName,
                                              sharePostUserLastName, sharePostUserProfilePicture, sharePostUserGender,
                                              tagCount, tagModels
                                      ));
                                  }

                                  counting++;
                                  isLoading = false;
                              }
                              else
                              {
                                  isLoading = true;
                              }

                              if (getActivity() != null)
                              {
                                  getActivity().runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          if (firstTime)
                                          {
                                              firstTime = false;
                                              showFooter.add(true);
                                              recyclerAdapter = new HomeRecyclerAdapter(getContext(), modelArrayList, showFooter, HomeFragment.this);
                                              rv_home.setAdapter(recyclerAdapter);
                                          }
                                          else
                                          {
                                              recyclerAdapter.notifyDataSetChanged();
                                          }
                                      }
                                  });
                              }
                          }
                          catch (JSONException e)
                          {
                              e.printStackTrace();
                          }
                      }
                  }
              });
          } catch (IOException e) {
              e.printStackTrace();
          }

    }*/

    private void loadData(final int count) {

        System.out.println("load_data_counting" + count);

        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", sp.getString("userId", "")).
                add("count", count + "").
                build();

        final Request request = new Request.Builder().
                url(CommonFunctions.FETCH_FEEDS).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (srl_home.isRefreshing()) {
                    System.out.println("Refreshing;......");
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (recyclerAdapter != null) {
                                    rv_home.getRecycledViewPool().clear();
                                    recyclerAdapter.notifyDataSetChanged();
                                    srl_home.setRefreshing(false);

                                }
                                modelArrayList.clear();
                                srl_home.setRefreshing(false);
                                System.out.println("modelArrayList_size_clear_after-" + modelArrayList.size());
                            }


                        });
                    }
                }

                String myResponse = response.body().string();
                try {
                    JSONArray ja = new JSONArray(myResponse);

                    System.out.println("load_data_ja_length" + ja.length());

                    if (ja.length() > 0) {

                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("no_post_visible_gone");

                                    text_home.setVisibility(View.GONE);

                                }
                            });
                        }


                        check = 1;
                        System.out.println("Home Fragment: Array  " + ja);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);

                            JSONObject post = jo.getJSONObject("post");
                            String type = post.getString("type");
                            if (type.equals("2")) {
                                System.out.println("this is viedow" + post);
                            }


                            System.out.println("Home Fragment: Post " + post);
                            JSONObject action = jo.getJSONObject("action");
                            System.out.println("Home Fragment: Action " + action);
                            JSONObject share = null;
                            JSONObject tag = null;
                            Log.e("jo", "onResponse: " + jo);
                            JSONArray likes = jo.getJSONArray("like");
                            Log.e("mlike", "onResponse: " + likes);
                            ArrayList<LikeDislikeModel> likeArrayList = new ArrayList<>();
                            if (likes.length() != 0) {
                                System.out.println("Home Fragment: Like " + likes);
                                for (int l = 0; l < likes.length(); l++) {
                                    LikeDislikeModel likeDislikeModel = new LikeDislikeModel();
                                    JSONObject like = likes.getJSONObject(l);
                                    String userId = like.getString("userId");
                                    likeDislikeModel.setUserId(userId);
                                    String firstName = like.getString("firstName");
                                    likeDislikeModel.setFirstName(firstName);
                                    String lastName = like.getString("lastName");
                                    likeDislikeModel.setLasttName(lastName);
                                    String profilePicture = like.getString("profilePicture");
                                    likeDislikeModel.setProfilePicture(profilePicture);
                                    likeArrayList.add(likeDislikeModel);
                                }
                            }

                            ArrayList<LikeDislikeModel> dislikeArrayList = new ArrayList<>();
                            JSONArray dislikes = jo.getJSONArray("dislike");

                            if (dislikes.length() != 0) {
                                System.out.println("Home Fragment: dislikes " + dislikes);
                                for (int l = 0; l < dislikes.length(); l++) {
                                    LikeDislikeModel likeDislikeModel = new LikeDislikeModel();
                                    JSONObject dislike = dislikes.getJSONObject(l);
                                    String userId = dislike.getString("userId");
                                    likeDislikeModel.setUserId(userId);
                                    String firstName = dislike.getString("firstName");
                                    likeDislikeModel.setFirstName(firstName);
                                    String lastName = dislike.getString("lastName");
                                    likeDislikeModel.setLasttName(lastName);
                                    String profilePicture = dislike.getString("profilePicture");
                                    likeDislikeModel.setProfilePicture(profilePicture);
                                    dislikeArrayList.add(likeDislikeModel);
                                }
                            }
                            if (post.getString("shareFlag").equalsIgnoreCase("1")) {
                                share = jo.getJSONObject("share");
                            }

                            if (post.getString("tagFlag").equalsIgnoreCase("1")) {
                                tag = jo.getJSONObject("tag");
                            }

                            String postId = post.getString("id");
                            String postType = post.getString("type");
                            String postUserId = post.getString("userId");


                            String postTotalLikes = post.getString("totalLikes");
                            String postTotalDislikes = post.getString("totalDislikes");

                            if (postTotalLikes.contains("-"))
                                postTotalLikes = "0";

                            if (postTotalDislikes.contains("-"))
                                postTotalDislikes = "0";


                            System.out.println("kaif_postTotalLikes" + postTotalLikes);
                            System.out.println("kaif_postTotalDislikes" + postTotalDislikes);


                            String postTotalComments = post.getString("totalComments");
                            String postTotalShares = post.getString("totalShares");
                            String postDescription = post.getString("description").trim();
                            String postImgFlag = post.getString("flag");
                            String device_token = post.getString("device_token");

                            String postLink = post.getString("fileLink");


                            ArrayList<String> postFileLinks = new ArrayList<>();
                            for (String str : postLink.split(",")) {
                                postFileLinks.add(str);
                            }
                            if (postFileLinks.size() > 0 && postFileLinks.get(0).equalsIgnoreCase("")) {
                                postFileLinks.remove(0);
                            }
                            Boolean isShared = post.getString("shareFlag").equals("1");
                            String postTime = post.getString("postTime");
                            String postUserFirstName = post.getString("firstName");
                            String postUserLastName = post.getString("lastName");
                            String postUserProfilePicture = post.getString("profilePicture");
                            Boolean postIsTagged = post.getString("tagFlag").equals("1");
                            String postUserGender = post.getString("gender");
                            String post_link = post.getString("post_url");

                            Boolean isMyLike = action.getString("like").equals("1");
                            Boolean isMyDislike = action.getString("dislike").equals("1");
                            Boolean isMyComment = action.getString("comment").equals("1");
                            Boolean isMyShare = action.getString("share").equals("1");

                            String sharePostId = share != null ? share.getString("id") : "";
                            String sharePostUserId = share != null ? share.getString("userId") : "";
                            String sharePostDescription = share != null ? share.getString("description").trim() : "";
                            String sharePostImgFlag = share != null ? share.getString("flag") : "";
                            String sharePostlink = share != null ? share.getString("fileLink") : "";
                            ArrayList<String> shareFileLinks = new ArrayList<>();
                            for (String str : sharePostlink.split(",")) {
                                shareFileLinks.add(str);
                            }
                            if (shareFileLinks.size() > 0 && shareFileLinks.get(0).equalsIgnoreCase("")) {
                                shareFileLinks.remove(0);
                            }
                            String sharePostTime = share != null ? share.getString("postTime") : "";
                            String sharePostUserFirstName = share != null ? share.getString("firstName") : "";
                            String sharePostUserLastName = share != null ? share.getString("lastName") : "";
                            String sharePostUserProfilePicture = share != null ? share.getString("profilePicture") : "";
                            String sharePostUserGender = share != null ? share.getString("gender") : "";

                            String tagCount = tag != null ? tag.getString("tagCount") : "0";
                            ArrayList<TagModel> tagModels = new ArrayList<>();
                            ;
                            if (tag != null) {
                                JSONArray jaTag = tag.getJSONArray("tagData");
                                for (int j = 0; j < jaTag.length(); j++) {
                                    JSONObject joTag = jaTag.getJSONObject(j);

                                    String tagFirstName = joTag.getString("firstName");
                                    String tagLastName = joTag.getString("lastName");
                                    String tagId = joTag.getString("id");

                                    tagModels.add(new TagModel(tagFirstName, tagLastName, tagId));
                                }
                            }
                            modelArrayList.add(new FeedModel(
                                    post_link, postId, postType, postUserId, postTotalLikes, postTotalDislikes,
                                    postTotalComments, postTotalShares, postDescription, postImgFlag,
                                    postFileLinks, isShared, postTime, postUserFirstName, postUserLastName,
                                    postUserProfilePicture, postIsTagged, postUserGender, isMyLike, isMyDislike,
                                    isMyComment, isMyShare, sharePostId, sharePostUserId, sharePostDescription,
                                    sharePostImgFlag, shareFileLinks, sharePostTime, sharePostUserFirstName,
                                    sharePostUserLastName, sharePostUserProfilePicture, sharePostUserGender,
                                    tagCount, tagModels, likeArrayList, dislikeArrayList, device_token
                            ));


                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (modelArrayList.size() > 0)
                                            text_home.setVisibility(View.GONE);

                                    }
                                });
                            }
                            System.out.println("modelArrayList_size_added_after-" + modelArrayList.size());
                            System.out.println("print_TOEKNE  " + i + device_token);

                        }
                        counting++;
                        isLoading = false;
                    } else {

                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override

                                public void run() {
                                    System.out.println("no_post_visible");
                                    System.out.println("recyclerAdapter == null-" + recyclerAdapter == null);


                                    if (recyclerAdapter == null)
                                        text_home.setVisibility(View.VISIBLE);
                                    else
                                        text_home.setVisibility(View.GONE);
                                    isLoading = true;
                                }
                            });
                        }


                    }

                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (firstTime) {
                                    System.out.println("firstime");
                                    firstTime = false;
                                    showFooter.add(true);
                                    recyclerAdapter = new HomeRecyclerAdapter(getContext(), modelArrayList, showFooter, HomeFragment.this, url);
                                    rv_home.setAdapter(recyclerAdapter);
                                    CommonFunctions.app_running = true;
                                    CommonFunctions.home_page_list = modelArrayList;
                                } else {
                                    System.out.println("second_time");
                                    System.out.println("modelArrayList_size-" + modelArrayList.size());
                                    CommonFunctions.app_running = true;
                                    CommonFunctions.home_page_list = modelArrayList;
                                    recyclerAdapter.notifyDataSetChanged();
                                }


                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void likeDislikeFeeds(final int position, String postId, final String type, final String device_token, final String postUser_id, final String postUserName) {
        System.out.println("like_api_token" + position);
        System.out.println("post_id" + postId);
        System.out.println("postUserName" + postUserName);
        System.out.println("liked_Api_called");

        final String userid = sp.getString("userId", "");

        MediaPlayer.create(getContext(), R.raw.beep3).start();

        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("userId", userid).
                add("id", postId).
                add("type", type).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.LIKE_DISLIKE_FEED).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("status").equals("1")) {


                        System.out.println("check_user_and_post_id");
                        //for send notification
                        if (device_token != null && !device_token.equals("")) {
                            if (!userid.equalsIgnoreCase(postUser_id)) {
                                System.out.println("like+userid" + userid);
                                System.out.println("like+postUserid" + postUser_id);

                                sendNotification_To_User(device_token, postUser_id, type, postUserName);
                            } else
                                System.out.println("same post's user id");
                        } else
                            System.out.println("Device_Token_Is_Null");


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void sendNotification_To_User(String device_token, String postuserid, String type, String postUsername) {

        String user_name = sp.getString("firstName", "")
                + " " + sp.getString("lastName", "");

        //String device_token = sp.getString("device_token", "");//firebase token id


        String message = "";
        String title = "Icosom";


        if (type.equalsIgnoreCase("like"))
            message = user_name + " has " + "liked on your post";

        else if (type.equalsIgnoreCase("dislike"))
            message = user_name + " has " + " disliked on your post";

        System.out.println("kaif_SendNotificationMethod");
        System.out.println("message" + message);
        System.out.println("title" + title);
        System.out.println("device_token" + device_token);
        System.out.println("postuserid" + postuserid);


        RequestBody body = new FormBody.Builder().
                add("send_to", "single").
                add("firebase_token", device_token).
                add("message", message).
                add("title", "icosom").
                add("image_url", "").
                add("action", "").
                add("user_id", postuserid).
                build();

        Request request = new Request.Builder().
                url("https://icosom.com/kaif_notification/newindex.php").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();

                System.out.println("notification_send_Success");
                /*try {
                    JSONObject jo = new JSONObject(myResponse);
                    if (jo.getString("success").equals("1")) {
                        Log.e("notification_success","");
                        System.out.println("notification_success"+jo);
                    }
                    else
                    {
                        Log.e("notification__failed","");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        });
    }

    public void getLikesDislikeList(ArrayList<LikeDislikeModel> likeList, ArrayList<LikeDislikeModel> dislikeList) {
        LikeDislikeFragment likeDislikeFragment = new LikeDislikeFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("likesList", likeList);
        args.putParcelableArrayList("dislikesList", dislikeList);
        likeDislikeFragment.setArguments(args);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, likeDislikeFragment)
                .addToBackStack(null)
                .commit();
    }

 /*   private void userInfo(String id) {

        RequestBody body = new FormBody.Builder().
                add("userId", id).
                build();

        Request request = new Request.Builder().
                url("http://icosom.com/wallet/main/dmrAndroidProcess.php?action=userInfo").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                if (!myResponse.isEmpty()) {
                    try {

                        final JSONObject ja = new JSONObject(myResponse);

                        Log.e("jaa", "run: " + ja);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                try {

                              *//*  if (ja.getString("status").equalsIgnoreCase("success")) {*//*
                                    Log.e("jaa", "run: " + ja);
                                    JSONArray jsonArray = ja.getJSONArray("data");
                                    JSONObject jaa = jsonArray.getJSONObject(0);
                                    Log.e("jaa", "run: " + jaa);
                                    edt.putString("userId", jaa.getString("id"));
                                    edt.putString("firstName", jaa.getString("firstName"));
                                    edt.putString("lastName", jaa.getString("lastName"));
                                    edt.putString("email", jaa.getString("email"));
                                    edt.putString("phone", jaa.getString("phone"));
                                    edt.putString("profiles", jaa.getString("profilePicture"));
                                    edt.putString("profile", jaa.getString("profilePicture"));
                                    edt.putString("cover", jaa.getString("coverPhoto"));
                                    edt.putString("covers", jaa.getString("coverPhoto"));
                                    edt.putString("birthDate", jaa.getString("birthDate"));
                                    edt.putString("country", jaa.getString("country"));
                                    edt.putString("state", jaa.getString("state"));
                                    edt.putString("city", jaa.getString("city"));
                                    edt.putString("gender", jaa.getString("gender"));
                                    edt.putString("address1", jaa.getString("address1"));
                                    edt.commit();
                                    url = CommonFunctions.FETCH_IMAGES + jaa.getString("profilePicture")
                                    ;
                                    Log.e("jaa", "run: " + jaa.getString("profilePicture"));


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }*/


    private void updateToken() {

        System.out.println("Token_Updated_method");

        String email = sp.getString("email", "");
        String password = sp.getString("password", "");
        String firebase_token = sp.getString("device_token", "");

        RequestBody body = new FormBody.Builder().
                add("data-source", "android").
                add("email", email).
                add("password", password).
                add("device_token", firebase_token).
                build();

        Request request = new Request.Builder().
                url(CommonFunctions.LOGIN).
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Errorrrrr " + e.getMessage());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //Toast.makeText(getBaseContext(), "Sorry! Not connected to internet ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String myResponse = response.body().string();
                try {
                    final JSONObject ja = new JSONObject(myResponse);
                    if (ja.length() != 0) {

                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    System.out.println("Token_Updated_success");
                                    //Toast.makeText(getBaseContext(), ""+ja.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}