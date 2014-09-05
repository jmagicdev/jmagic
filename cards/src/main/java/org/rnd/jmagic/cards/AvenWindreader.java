package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Aven Windreader")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.WIZARD, SubType.BIRD})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Odyssey.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class AvenWindreader extends Card
{
	public static final class Peek extends ActivatedAbility
	{
		public Peek(GameState state)
		{
			super(state, "(1)(U): Target player reveals the top card of his or her library.");

			this.setManaCost(new ManaPool("1U"));

			Target target = this.addTarget(Players.instance(), "target player");

			EventType.ParameterMap revealParameters = new EventType.ParameterMap();
			revealParameters.put(EventType.Parameter.CAUSE, This.instance());
			revealParameters.put(EventType.Parameter.OBJECT, TopCards.instance(1, LibraryOf.instance(targetedBy(target))));
			this.addEffect(new EventFactory(EventType.REVEAL, revealParameters, "Target player reveals the top card of his or her library"));
		}
	}

	public AvenWindreader(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new Peek(state));
	}
}
