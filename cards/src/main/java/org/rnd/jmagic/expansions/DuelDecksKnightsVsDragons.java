package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Duel Decks: Knights vs. Dragons")
public final class DuelDecksKnightsVsDragons extends SimpleExpansion
{
	public DuelDecksKnightsVsDragons()
	{
		super();

		this.addCards(Rarity.LAND, "Forest", "Mountain", "Plains");
		this.addCards(Rarity.COMMON, "Armillary Sphere", "Benalish Lancer", "Bogardan Rager", "Caravan Escort", "Cinder Wall", "Claws of Valakut", "Dragon Fodder", "Edge of Autumn", "Fiery Fall", "Fire-Belly Changeling", "Ghostfire", "Knight of Cliffhaven", "Lionheart Maverick", "Mighty Leap", "Mudbutton Torchrunner", "Oblivion Ring", "Plover Knights", "Seething Song", "Seismic Strike", "Sejiri Steppe", "Selesnya Sanctuary", "Sigil Blessing", "Skirk Prospector", "Skyhunter Patrol", "Spidersilk Armor", "Spitting Earth", "Steward of Valeron");
		this.addCards(Rarity.UNCOMMON, "Alaborn Cavalier", "Bloodmark Mentor", "Breath of Darigaaz", "Captive Flame", "Cone of Flame", "Dragon Whelp", "Dragon's Claw", "Dragonspeaker Shaman", "Grasslands", "Griffin Guide", "Harm's Way", "Henge Guardian", "Heroes' Reunion", "Jaws of Stone", "Juniper Order Ranger", "Kabira Vindicator", "Knight of Meadowgrain", "Leonin Skyhunter", "Paladin of Prahv", "Punishing Fire", "Reciprocate", "Reprisal", "Shiv's Embrace", "Silver Knight", "Temporary Insanity", "Test of Faith", "Treetop Village", "White Knight", "Wilt-Leaf Cavaliers", "Zhalfirin Commander");
		this.addCards(Rarity.RARE, "Kilnmouth Dragon", "Kinsbaile Cavalier", "Knight Exemplar", "Knight of the White Orchid", "Knotvine Paladin", "Loxodon Warhammer", "Mordant Dragon", "Shivan Hellkite", "Thunder Dragon", "Voracious Dragon");
		this.addCards(Rarity.MYTHIC, "Bogardan Hellkite", "Knight of the Reliquary");
	}
}
