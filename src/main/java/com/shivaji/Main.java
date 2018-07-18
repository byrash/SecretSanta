package com.shivaji;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/** @author Shivaji Byrapaneni */
public class Main {

  public Map<String, String> assignSantaSurprises(final Set<String> names) {
    final Random random = new Random();
    final Map<String, String> santaSurprises = new HashMap<>(names.size());
    final List<String> copyOfNames = new ArrayList<>(names);
    if (names.size() < 3) {
      throw new IllegalArgumentException("Not enough names");
    }

    names
        .stream()
        .forEach(
            giversName -> {
              String receiversNameGuessed;
              do {
                int nextRandomInt = random.nextInt(copyOfNames.size());
                receiversNameGuessed = copyOfNames.get(nextRandomInt);
                if (isGiverAndReceiverEligible(
                    santaSurprises,
                    giversName,
                    receiversNameGuessed)) { // Can"t gift to self & cant be cyclic
                  santaSurprises.put(giversName, receiversNameGuessed);
                  copyOfNames.remove(receiversNameGuessed); // Receiver cant be gifted twice
                  break;
                } else if (copyOfNames.size()
                    == 1) { // Odd case where only 1 name left and that cant be assigned
                  santaSurprises.clear();
                  copyOfNames.clear();
                  santaSurprises.putAll(assignSantaSurprises(names));
                }
              } while (!isGiverAndReceiverEligible(santaSurprises, giversName, receiversNameGuessed)
                  && santaSurprises.size() < names.size());
            });

    return santaSurprises;
  }

  private boolean isGiverAndReceiverEligible(
      Map<String, String> santaSurprises, String giverName, String receiverNameGuessed) {
    return !isGiftingToSelf(giverName, receiverNameGuessed)
        && !isReceiverAlreadyGifted(santaSurprises, receiverNameGuessed)
        && !isGiverAndReceiverCyclic(santaSurprises, giverName, receiverNameGuessed);
  }

  private boolean isReceiverAlreadyGifted(
      Map<String, String> santaSurprises, String receiverNameGuessed) {
    return santaSurprises.containsValue(receiverNameGuessed);
  }

  private boolean isGiverAndReceiverCyclic(
      Map<String, String> santaSurprises, String giverName, String receiverNameGuessed) {
    return santaSurprises.get(receiverNameGuessed) != null
        && santaSurprises.get(receiverNameGuessed).equalsIgnoreCase(giverName);
  }

  private boolean isGiftingToSelf(String giverName, String receiverNameGuessed) {
    return giverName.equalsIgnoreCase(receiverNameGuessed);
  }
}
