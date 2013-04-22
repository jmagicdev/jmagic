package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Avenger of Zendikar")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("5GG")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
public final class AvengerofZendikar extends Card
{
	public static final class MakePlants extends EventTriggeredAbility
	{
		public MakePlants(GameState state)
		{
			super(state, "When Avenger of Zendikar enters the battlefield, put a 0/1 green Plant creature token onto the battlefield for each land you control.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator forEachLandYouControl = Count.instance(Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance())));
			String effectName = "Put a 0/1 green Plant creature token onto the battlefield for each land you control.";
			CreateTokensFactory tokens = new CreateTokensFactory(forEachLandYouControl, numberGenerator(0), numberGenerator(1), effectName);
			tokens.setColors(Color.GREEN);
			tokens.setSubTypes(SubType.PLANT);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public static final class PlantsBigger extends EventTriggeredAbility
	{
		public PlantsBigger(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, you may put a +1/+1 counter on each Plant creature you control.");
			this.addPattern(landfall());
			EventFactory counters = putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, Intersect.instance(HasSubType.instance(SubType.PLANT), Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(You.instance()))), "Put a +1/+1 counter on each Plant creature you control.");
			this.addEffect(youMay(counters, "You may put a +1/+1 counter on each Plant creature you control."));
		}

	}

	public AvengerofZendikar(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.addAbility(new MakePlants(state));
		this.addAbility(new PlantsBigger(state));
	}
}
