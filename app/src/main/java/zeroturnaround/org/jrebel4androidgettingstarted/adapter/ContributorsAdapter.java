package zeroturnaround.org.jrebel4androidgettingstarted.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zeroturnaround.org.jrebel4androidgettingstarted.R;
import zeroturnaround.org.jrebel4androidgettingstarted.service.Contributor;

/**
 * Created by Sten on 17/02/16.
 */
public class ContributorsAdapter extends BaseAdapter {

    private List<Contributor> contributorList = new ArrayList<>();
    private Context context;

    public ContributorsAdapter(Context context, List<Contributor> contributorList) {
        this.contributorList = contributorList;
        this.context = context;
    }

    public ContributorsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Contributor getItem(int position) {
        return contributorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return contributorList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        Contributor contributor = getItem(position);

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.contributors_list_item, null);

            viewHolder = new ViewHolder();
            viewHolder.contributorNameTextView = (TextView) convertView.findViewById(R.id.contributor_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.contributorNameTextView.setText(contributor.getLogin());

        return convertView;

    }

    public void setContributorListAndNotify(List<Contributor> contributorList) {
        this.contributorList = contributorList;
        notifyDataSetChanged();
    }


    public static class ViewHolder {

        private TextView contributorNameTextView;
    }
}