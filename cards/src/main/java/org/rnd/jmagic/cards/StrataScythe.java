package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Strata Scythe")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@ColorIdentity({})
public final class StrataScythe extends Card
{
	public static final class StrataScytheAbility0 extends EventTriggeredAbility
	{
		public StrataScytheAbility0(GameState state)
		{
			super(state, "Imprint \u2014 When Strata Scythe enters the battlefield, search your library for a land card, exile it, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a land card, exile it, then shuffle your library.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TO, ExileZone.instance());
			factory.parameters.put(EventType.Parameter.TYPE, HasType.instance(Type.LAND));
			factory.setLink(this);
			this.addEffect(factory);

			this.getLinkManager().addLinkClass(StrataScytheAbility1.class);
		}
	}

	public static final class StrataScytheAbility1 extends StaticAbility
	{
		public StrataScytheAbility1(GameState state)
		{
			super(state, "Equipped creature gets +1/+1 for each land on the battlefield with the same name as the exiled card.");

			SetGenerator name = NameOf.instance(ChosenFor.instance(LinkedTo.instance(Identity.instance(This.instance()))));
			SetGenerator number = Count.instance(Intersect.instance(LandPermanents.instance(), HasName.instance(name)));

			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), number, number));

			this.getLinkManager().addLinkClass(StrataScytheAbility0.class);
		}
	}

	public StrataScythe(GameState state)
	{
		super(state);

		// Imprint \u2014 When Strata Scythe enters the battlefield, search your
		// library for a land card, exile it, then shuffle your library.
		this.addAbility(new StrataScytheAbility0(state));

		// Equipped creature gets +1/+1 for each land on the battlefield with
		// the same name as the exiled card.
		this.addAbility(new StrataScytheAbility1(state));

		// Equip (3)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
