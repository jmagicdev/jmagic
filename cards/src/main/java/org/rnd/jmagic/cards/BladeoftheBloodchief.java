package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Blade of the Bloodchief")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({})
public final class BladeoftheBloodchief extends Card
{
	public static final class BloodOfTheBladechief extends EventTriggeredAbility
	{
		public BloodOfTheBladechief(GameState state)
		{
			super(state, "Whenever a creature dies, put a +1/+1 counter on equipped creature. If equipped creature is a Vampire, put two +1/+1 counters on it instead.");

			this.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), HasType.instance(Type.CREATURE), true));

			SetGenerator equippedCreature = EquippedBy.instance(ABILITY_SOURCE_OF_THIS);
			SetGenerator numCounters = IfThenElse.instance(Intersect.instance(HasSubType.instance(SubType.VAMPIRE), equippedCreature), numberGenerator(2), numberGenerator(1));
			this.addEffect(putCounters(numCounters, Counter.CounterType.PLUS_ONE_PLUS_ONE, equippedCreature, "Put a +1/+1 counter on equipped creature. If equipped creature is a Vampire, put two +1/+1 counters on it instead."));
		}
	}

	public BladeoftheBloodchief(GameState state)
	{
		super(state);

		this.addAbility(new BloodOfTheBladechief(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
