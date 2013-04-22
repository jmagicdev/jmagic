package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Phantasmal Image")
@Types({Type.CREATURE})
@SubTypes({SubType.ILLUSION})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class PhantasmalImage extends Card
{
	public static final class PhantasmalAbility extends EventTriggeredAbility
	{
		public PhantasmalAbility(GameState state)
		{
			super(state, "When this creature becomes the target of a spell or ability, sacrifice it.");
			this.addPattern(new BecomesTheTargetPattern(ABILITY_SOURCE_OF_THIS));
			this.addEffect(sacrificeThis("this creature"));
		}
	}

	public PhantasmalImage(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// You may have Phantasmal Image enter the battlefield as a copy of any
		// creature on the battlefield, except it's an Illusion in addition to
		// its other types and it gains "When this creature becomes the target
		// of a spell or ability, sacrifice it."
		this.addAbility(new org.rnd.jmagic.abilities.YouMayHaveThisEnterTheBattlefieldAsACopy(CreaturePermanents.instance()).setName("You may have Phantasmal Image enter the battlefield as a copy of any creature on the battlefield, except it's an Illusion in addition to its other types and it gains \"When this creature becomes the target of a spell or ability, sacrifice it.\"").setAdditionalSubTypes(SubType.ILLUSION).setAbility(PhantasmalAbility.class).getStaticAbility(state));
	}
}
