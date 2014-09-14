package org.rnd.jmagic.gameTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.expansions.*;

public class Modern extends GameType
{
	public Modern()
	{
		super("Modern");

		this.addRule(new DeckSizeMinimum(60));
		this.addRule(new MaximumCardCount(4));
		this.addRule(new SideboardSize(15));
		this.addRule(new SideboardAsWishboard());
		this.addRule(new CardPool());
	}

	@Name("Modern")
	@Description("Modern, as of 2012 September 20 (Eighth Edition through Return to Ravnica)")
	public static class CardPool extends org.rnd.jmagic.engine.gameTypes.CardPool
	{
		public CardPool()
		{
			super(false);

			this.allowSet(EighthEdition.class);
			this.allowSet(Mirrodin.class);
			this.allowSet(Darksteel.class);
			this.allowSet(FifthDawn.class);
			this.allowSet(ChampionsOfKamigawa.class);
			this.allowSet(BetrayersOfKamigawa.class);
			this.allowSet(SaviorsOfKamigawa.class);
			this.allowSet(NinthEdition.class);
			this.allowSet(RavnicaCityOfGuilds.class);
			this.allowSet(Guildpact.class);
			this.allowSet(Dissension.class);
			this.allowSet(Coldsnap.class);
			this.allowSet(TimeSpiral.class);
			this.allowSet(TimeSpiralTimeshifted.class);
			this.allowSet(PlanarChaos.class);
			this.allowSet(FutureSight.class);
			this.allowSet(TenthEdition.class);
			this.allowSet(Lorwyn.class);
			this.allowSet(Morningtide.class);
			this.allowSet(Shadowmoor.class);
			this.allowSet(Eventide.class);
			this.allowSet(ShardsOfAlara.class);
			this.allowSet(Conflux.class);
			this.allowSet(AlaraReborn.class);
			this.allowSet(Magic2010.class);
			this.allowSet(Zendikar.class);
			this.allowSet(Worldwake.class);
			this.allowSet(RiseOfTheEldrazi.class);
			this.allowSet(Magic2011.class);
			this.allowSet(ScarsOfMirrodin.class);
			this.allowSet(MirrodinBesieged.class);
			this.allowSet(NewPhyrexia.class);
			this.allowSet(Magic2012.class);
			this.allowSet(Innistrad.class);
			this.allowSet(DarkAscension.class);
			this.allowSet(AvacynRestored.class);
			this.allowSet(Magic2013.class);
			this.allowSet(ReturnToRavnica.class);
			this.allowSet(Gatecrash.class);
			this.allowSet(DragonsMaze.class);
			this.allowSet(Magic2014CoreSet.class);
			this.allowSet(Theros.class);
			this.allowSet(BornOfTheGods.class);
			this.allowSet(JourneyIntoNyx.class);
			this.allowSet(Magic2015CoreSet.class);

			this.banCard("Ancestral Vision");
			this.banCard("Ancient Den");
			this.banCard("Blazing Shoal");
			this.banCard("Bloodbraid Elf");
			this.banCard("Chrome Mox");
			this.banCard("Cloudpost");
			this.banCard("Dark Depths");
			this.banCard("Deathrite Shaman");
			this.banCard("Dread Return");
			this.banCard("Glimpse of Nature");
			this.banCard("Golgari Grave-Troll");
			this.banCard("Great Furnace");
			this.banCard("Green Sun's Zenith");
			this.banCard("Hypergenesis");
			this.banCard("Jace, the Mind Sculptor");
			this.banCard("Mental Misstep");
			this.banCard("Ponder");
			this.banCard("Preordain");
			this.banCard("Punishing Fire");
			this.banCard("Rite of Flame");
			this.banCard("Seat of the Synod");
			this.banCard("Second Sunrise");
			this.banCard("Seething Song");
			this.banCard("Sensei's Divining Top");
			this.banCard("Stoneforge Mystic");
			this.banCard("Skullclamp");
			this.banCard("Sword of the Meek");
			this.banCard("Tree of Tales");
			this.banCard("Umezawa's Jitte");
			this.banCard("Vault of Whispers");
		}
	}
}
