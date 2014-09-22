package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ravager of the Fells")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@ColorIdentity({Color.RED, Color.GREEN})
public final class RavageroftheFells extends AlternateCard
{
	public static final class RavageroftheFellsAbility1 extends EventTriggeredAbility
	{
		public RavageroftheFellsAbility1(GameState state)
		{
			super(state, "Whenever this creature transforms into Ravager of the Fells, it deals 2 damage to target opponent and 2 damage to up to one target creature that player controls.");

			org.rnd.jmagic.engine.patterns.SimpleEventPattern pattern = new org.rnd.jmagic.engine.patterns.SimpleEventPattern(EventType.TRANSFORM_ONE_PERMANENT);
			pattern.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);

			Target targetPlayer = this.addTarget(Players.instance(), "target player");
			Target targetCreature = this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(targetedBy(targetPlayer))), "up to one target creature that player controls");
			targetCreature.setNumber(0, 1);

			this.addEffect(permanentDealDamage(2, Union.instance(targetedBy(targetPlayer), targetedBy(targetCreature)), "It deals 2 damage to target opponent and 2 damage to up to one target creature that player controls."));
		}
	}

	public RavageroftheFells(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.setColorIndicator(Color.GREEN, Color.RED);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Whenever this creature transforms into Ravager of the Fells, it deals
		// 2 damage to target opponent and 2 damage to up to one target creature
		// that player controls.
		this.addAbility(new RavageroftheFellsAbility1(state));

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Ravager of the Fells.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
