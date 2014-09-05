package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Nomad Mythmaker")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.NOMAD, SubType.CLERIC})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Judgment.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class NomadMythmaker extends Card
{
	public static final class PutAuraOntoTheBattlefield extends ActivatedAbility
	{
		public PutAuraOntoTheBattlefield(GameState state)
		{
			super(state, "(W), (T): Put target Aura card from a graveyard onto the battlefield under your control attached to a creature you control.");

			this.setManaCost(new ManaPool("W"));
			this.costsTap = true;

			SetGenerator auras = HasSubType.instance(SubType.AURA);
			SetGenerator inGraveyards = InZone.instance(GraveyardOf.instance(Players.instance()));
			SetGenerator aurasInGraveyards = Intersect.instance(auras, inGraveyards);
			Target target = this.addTarget(aurasInGraveyards, "target Aura card from a graveyard");

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			parameters.put(EventType.Parameter.CHOICE, CREATURES_YOU_CONTROL);
			this.addEffect(new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_ATTACHED_TO_CHOICE, parameters, "Put target Aura card from a graveyard onto the battlefield under your control attached to a creature you control."));
		}
	}

	public NomadMythmaker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new PutAuraOntoTheBattlefield(state));
	}
}
