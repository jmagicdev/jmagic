package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Xenograft")
@Types({Type.ENCHANTMENT})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Xenograft extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Xenograft", "Choose a creature type.", true);

	public static final class XenograftAbility0 extends org.rnd.jmagic.abilities.AsThisEntersTheBattlefieldChooseACreatureType
	{
		public XenograftAbility0(GameState state)
		{
			super(state, "As Xenograft enters the battlefield, choose a creature type.");

			this.getLinkManager().addLinkClass(XenograftAbility1.class);
		}
	}

	public static final class XenograftAbility1 extends StaticAbility
	{
		public XenograftAbility1(GameState state)
		{
			super(state, "Each creature you control is the chosen type in addition to its other types.");

			this.getLinkManager().addLinkClass(XenograftAbility0.class);

			SetGenerator chosenType = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));
			ContinuousEffect.Part addType = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			addType.parameters.put(ContinuousEffectType.Parameter.OBJECT, CREATURES_YOU_CONTROL);
			addType.parameters.put(ContinuousEffectType.Parameter.TYPE, chosenType);
			this.addEffectPart(addType);
		}
	}

	public Xenograft(GameState state)
	{
		super(state);

		// As Xenograft enters the battlefield, choose a creature type.
		this.addAbility(new XenograftAbility0(state));

		// Each creature you control is the chosen type in addition to its other
		// types.
		this.addAbility(new XenograftAbility1(state));
	}
}
