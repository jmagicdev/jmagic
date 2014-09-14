package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Black Oak of Odunos")
@Types({Type.CREATURE})
@SubTypes({SubType.TREEFOLK, SubType.ZOMBIE})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class BlackOakofOdunos extends Card
{
	public static final class BlackOakofOdunosAbility1 extends ActivatedAbility
	{
		public BlackOakofOdunosAbility1(GameState state)
		{
			super(state, "(B), Tap another untapped creature you control: Black Oak of Odunos gets +1/+1 until end of turn.");
			this.setManaCost(new ManaPool("(B)"));

			SetGenerator yourUntappedCreatures = RelativeComplement.instance(Intersect.instance(Untapped.instance(), CREATURES_YOU_CONTROL), ABILITY_SOURCE_OF_THIS);

			EventFactory tapACreature = new EventFactory(EventType.TAP_CHOICE, "Tap an untapped creature you control");
			tapACreature.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tapACreature.parameters.put(EventType.Parameter.PLAYER, You.instance());
			tapACreature.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			tapACreature.parameters.put(EventType.Parameter.CHOICE, yourUntappedCreatures);
			this.addCost(tapACreature);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +1, "Black Oak of Odunos gets +1/+1 until end of turn."));
		}
	}

	public BlackOakofOdunos(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(5);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (B), Tap another untapped creature you control: Black Oak of Odunos
		// gets +1/+1 until end of turn.
		this.addAbility(new BlackOakofOdunosAbility1(state));
	}
}
