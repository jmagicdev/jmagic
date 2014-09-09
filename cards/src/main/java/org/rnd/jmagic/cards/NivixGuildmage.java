package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nivix Guildmage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("UR")
@ColorIdentity({Color.BLUE, Color.RED})
public final class NivixGuildmage extends Card
{
	public static final class NivixGuildmageAbility0 extends ActivatedAbility
	{
		public NivixGuildmageAbility0(GameState state)
		{
			super(state, "(1)(U)(R): Draw a card, then discard a card.");
			this.setManaCost(new ManaPool("(1)(U)(R)"));

			this.addEffect(drawCards(You.instance(), 1, "Draw a card,"));
			this.addEffect(discardCards(You.instance(), 1, "then discard a card."));
		}
	}

	public static final class NivixGuildmageAbility1 extends ActivatedAbility
	{
		public NivixGuildmageAbility1(GameState state)
		{
			super(state, "(2)(U)(R): Copy target instant or sorcery spell you control. You may choose new targets for the copy.");
			this.setManaCost(new ManaPool("(2)(U)(R)"));

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), ControlledBy.instance(You.instance(), Stack.instance())), "target instant or sorcery spell you control"));

			EventFactory factory = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy target instant or sorcery spell you control. You may choose new targets for the copy.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, target);
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(factory);
		}
	}

	public NivixGuildmage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (1)(U)(R): Draw a card, then discard a card.
		this.addAbility(new NivixGuildmageAbility0(state));

		// (2)(U)(R): Copy target instant or sorcery spell you control. You may
		// choose new targets for the copy.
		this.addAbility(new NivixGuildmageAbility1(state));
	}
}
