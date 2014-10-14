package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tethmos High Priest")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.CAT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class TethmosHighPriest extends Card
{
	public static final class TethmosHighPriestAbility0 extends EventTriggeredAbility
	{
		public TethmosHighPriestAbility0(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets Tethmos High Priest, return target creature card with converted mana cost 2 or less from your graveyard to the battlefield.");
			this.addPattern(heroic());

			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
			SetGenerator smallDeadThings = Intersect.instance(deadThings, HasConvertedManaCost.instance(Between.instance(null, 2)));
			SetGenerator target = targetedBy(this.addTarget(smallDeadThings, "target creature card with converted mana cost 2 or less from your graveyard"));

			this.addEffect(putOntoBattlefield(target, "Return target creature card with converted mana cost 2 or less from your graveyard to the battlefield."));
		}
	}

	public TethmosHighPriest(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Heroic \u2014 Whenever you cast a spell that targets Tethmos High
		// Priest, return target creature card with converted mana cost 2 or
		// less from your graveyard to the battlefield.
		this.addAbility(new TethmosHighPriestAbility0(state));
	}
}
