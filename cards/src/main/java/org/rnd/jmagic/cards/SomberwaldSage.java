package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Somberwald Sage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.DRUID})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class SomberwaldSage extends Card
{
	public static final class SomberwaldSageAbility0 extends ActivatedAbility
	{
		public SomberwaldSageAbility0(GameState state)
		{
			super(state, "(T): Add three mana of any one color to your mana pool. Spend this mana only to cast creature spells.");
			this.costsTap = true;

			EventFactory mana = new EventFactory(ADD_RESTRICTED_MANA, "Add three mana of any one color to your mana pool. Spend this mana only to cast creature spells.");
			mana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			mana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			mana.parameters.put(EventType.Parameter.TYPE, Identity.instance(new TypePattern(Type.CREATURE)));
			mana.parameters.put(EventType.Parameter.PERMANENT, Empty.instance());
			mana.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(Color.allColors()));
			mana.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			this.addEffect(mana);
		}
	}

	public SomberwaldSage(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// (T): Add three mana of any one color to your mana pool. Spend this
		// mana only to cast creature spells.
		this.addAbility(new SomberwaldSageAbility0(state));
	}
}
