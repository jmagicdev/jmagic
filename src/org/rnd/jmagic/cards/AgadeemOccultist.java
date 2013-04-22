package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Agadeem Occultist")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN, SubType.ALLY})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class AgadeemOccultist extends Card
{
	public static final class AgadeemOccultistAbility0 extends ActivatedAbility
	{
		public AgadeemOccultistAbility0(GameState state)
		{
			super(state, "(T): Put target creature card from an opponent's graveyard onto the battlefield under your control if its converted mana cost is less than or equal to the number of Allies you control.");
			this.costsTap = true;

			SetGenerator creatureCards = HasType.instance(Type.CREATURE);
			SetGenerator inOpponentsYard = InZone.instance(GraveyardOf.instance(OpponentsOf.instance(You.instance())));
			Target t = this.addTarget(Intersect.instance(creatureCards, inOpponentsYard), "target creature card from an opponent's graveyard");

			EventFactory move = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Put target creature card from an opponent's graveyard onto the battlefield under your control");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			move.parameters.put(EventType.Parameter.OBJECT, targetedBy(t));

			SetGenerator lessThanOrEqualNumAllies = Between.instance(null, Count.instance(ALLIES_YOU_CONTROL));
			SetGenerator condition = Intersect.instance(ConvertedManaCostOf.instance(targetedBy(t)), lessThanOrEqualNumAllies);

			EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "Put target creature card from an opponent's graveyard onto the battlefield under your control if its converted mana cost is less than or equal to the number of Allies you control.");
			effect.parameters.put(EventType.Parameter.IF, condition);
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(move));
			this.addEffect(effect);
		}
	}

	public AgadeemOccultist(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(2);

		// (T): Put target creature card from an opponent's graveyard onto the
		// battlefield under your control if its converted mana cost is less
		// than or equal to the number of Allies you control.
		this.addAbility(new AgadeemOccultistAbility0(state));
	}
}
