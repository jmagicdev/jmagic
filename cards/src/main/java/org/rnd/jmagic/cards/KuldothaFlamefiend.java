package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Kuldotha Flamefiend")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class KuldothaFlamefiend extends Card
{
	public static final class KuldothaFlamefiendAbility0 extends EventTriggeredAbility
	{
		public KuldothaFlamefiendAbility0(GameState state)
		{
			super(state, "When Kuldotha Flamefiend enters the battlefield, you may sacrifice an artifact. If you do, Kuldotha Flamefiend deals 4 damage divided as you choose among any number of target creatures and/or players.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creatures and/or players");
			target.setNumber(1, 4);

			this.setDivision(Union.instance(numberGenerator(4), Identity.instance("damage")));

			EventFactory damage = new EventFactory(EventType.DISTRIBUTE_DAMAGE, "Kuldotha Flamefiend deals 4 damage divided as you choose among any number of target creatures and/or players.");
			damage.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			damage.parameters.put(EventType.Parameter.TAKER, ChosenTargetsFor.instance(Identity.instance(target), This.instance()));

			EventFactory factory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may sacrifice an artifact. If you do, Kuldotha Flamefiend deals 4 damage divided as you choose among any number of target creatures and/or players.");
			factory.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(sacrifice(You.instance(), 1, HasType.instance(Type.ARTIFACT), "Sacrifice an artifact."), "You may sacrifice an artifact.")));
			factory.parameters.put(EventType.Parameter.THEN, Identity.instance(damage));
			this.addEffect(factory);
		}
	}

	public KuldothaFlamefiend(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// When Kuldotha Flamefiend enters the battlefield, you may sacrifice an
		// artifact. If you do, Kuldotha Flamefiend deals 4 damage divided as
		// you choose among any number of target creatures and/or players.
		this.addAbility(new KuldothaFlamefiendAbility0(state));
	}
}
