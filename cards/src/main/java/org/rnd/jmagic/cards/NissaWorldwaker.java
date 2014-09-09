package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nissa, Worldwaker")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.NISSA})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class NissaWorldwaker extends Card
{
	public static final class NissaWorldwakerAbility0 extends LoyaltyAbility
	{
		public NissaWorldwakerAbility0(GameState state)
		{
			super(state, +1, "Target land you control becomes a 4/4 Elemental creature with trample. It's still a land.");

			SetGenerator yourLands = Intersect.instance(HasType.instance(Type.LAND), ControlledBy.instance(You.instance()));
			SetGenerator target = targetedBy(this.addTarget(yourLands, "target land"));

			Animator elemental = new Animator(target, 4, 4);
			elemental.addSubType(SubType.ELEMENTAL);
			elemental.addAbility(org.rnd.jmagic.abilities.keywords.Trample.class);
			this.addEffect(createFloatingEffect("Target land you control becomes a 4/4 Elemental creature with trample. It's still a land.", elemental.getParts()));
		}
	}

	public static final class NissaWorldwakerAbility1 extends LoyaltyAbility
	{
		public NissaWorldwakerAbility1(GameState state)
		{
			super(state, +1, "Untap up to four target Forests.");

			SetGenerator forests = Intersect.instance(Permanents.instance(), HasSubType.instance(SubType.FOREST));
			Target targetForests = this.addTarget(forests, "up to four target Forests");
			targetForests.setNumber(0, 4);
			SetGenerator target = targetedBy(targetForests);
			this.addEffect(untap(target, "Untap up to four target Forests."));
		}
	}

	public static final class NissaWorldwakerAbility2 extends LoyaltyAbility
	{
		public NissaWorldwakerAbility2(GameState state)
		{
			super(state, -7, "Search your library for any number of basic land cards, put them onto the battlefield, then shuffle your library. Those lands become 4/4 Elemental creatures with trample. They're still lands.");

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for any number of basic land cards, put them onto the battlefield, then shuffle your library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, null));
			search.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
			this.addEffect(search);

			SetGenerator thoseLands = EffectResult.instance(search);

			Animator elemental = new Animator(thoseLands, 4, 4);
			elemental.addSubType(SubType.ELEMENTAL);
			elemental.addAbility(org.rnd.jmagic.abilities.keywords.Trample.class);
			this.addEffect(createFloatingEffect("Those lands become 4/4 Elemental creatures with trample. They're still lands.", elemental.getParts()));
		}
	}

	public NissaWorldwaker(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(3);

		// +1: Target land you control becomes a 4/4 Elemental creature with
		// trample. It's still a land.
		this.addAbility(new NissaWorldwakerAbility0(state));

		// +1: Untap up to four target Forests.
		this.addAbility(new NissaWorldwakerAbility1(state));

		// -7: Search your library for any number of basic land cards, put them
		// onto the battlefield, then shuffle your library. Those lands become
		// 4/4 Elemental creatures with trample. They're still lands.
		this.addAbility(new NissaWorldwakerAbility2(state));
	}
}
