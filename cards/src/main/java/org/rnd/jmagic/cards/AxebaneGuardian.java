package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Axebane Guardian")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.DRUID})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class AxebaneGuardian extends Card
{
	public static final class AxebaneGuardianAbility1 extends ActivatedAbility
	{
		public AxebaneGuardianAbility1(GameState state)
		{
			super(state, "(T): Add X mana in any combination of colors to your mana pool, where X is the number of creatures with defender you control.");
			this.costsTap = true;

			SetGenerator X = Count.instance(Intersect.instance(CREATURES_YOU_CONTROL, HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Defender.class)));

			EventFactory mana = new EventFactory(EventType.ADD_MANA, "Add X mana in any combination of colors to your mana pool, where X is the number of creatures with defender you control.");
			mana.parameters.put(EventType.Parameter.SOURCE, This.instance());
			mana.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("(WUBRG)")));
			mana.parameters.put(EventType.Parameter.MULTIPLY, X);
			mana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(mana);
		}
	}

	public AxebaneGuardian(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (T): Add X mana in any combination of colors to your mana pool, where
		// X is the number of creatures with defender you control.
		this.addAbility(new AxebaneGuardianAbility1(state));
	}
}
