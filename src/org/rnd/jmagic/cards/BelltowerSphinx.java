package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Belltower Sphinx")
@Types({Type.CREATURE})
@SubTypes({SubType.SPHINX})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class BelltowerSphinx extends Card
{
	public static final class BelltowerSphinxAbility1 extends EventTriggeredAbility
	{
		public BelltowerSphinxAbility1(GameState state)
		{
			super(state, "Whenever a source deals damage to Belltower Sphinx, that source's controller puts that many cards from the top of his or her library into his or her graveyard.");
			this.addPattern(new SimpleDamagePattern(null, ABILITY_SOURCE_OF_THIS));

			SetGenerator damage = TriggerDamage.instance(This.instance());
			SetGenerator source = SourceOfDamage.instance(damage);
			SetGenerator controller = ControllerOf.instance(source);
			SetGenerator thatMany = Count.instance(damage);

			this.addEffect(millCards(controller, thatMany, "That source's controller puts that many cards from the top of his or her library into his or her graveyard."));
		}
	}

	public BelltowerSphinx(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever a source deals damage to Belltower Sphinx, that source's
		// controller puts that many cards from the top of his or her library
		// into his or her graveyard.
		this.addAbility(new BelltowerSphinxAbility1(state));
	}
}
