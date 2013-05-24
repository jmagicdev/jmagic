package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Phantasmal Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.ILLUSION, SubType.DRAGON})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class PhantasmalDragon extends Card
{
	public static final class PhantasmalDragonAbility1 extends EventTriggeredAbility
	{
		public PhantasmalDragonAbility1(GameState state)
		{
			super(state, "When Phantasmal Dragon becomes the target of a spell or ability, sacrifice it.");
			this.addPattern(new BecomesTheTargetPattern(ABILITY_SOURCE_OF_THIS));
			this.addEffect(sacrificeThis("Phantasmal Dragon"));
		}
	}

	public PhantasmalDragon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Phantasmal Dragon becomes the target of a spell or ability,
		// sacrifice it.
		this.addAbility(new PhantasmalDragonAbility1(state));
	}
}
